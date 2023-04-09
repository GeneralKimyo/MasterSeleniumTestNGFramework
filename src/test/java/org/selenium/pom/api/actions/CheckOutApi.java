package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.api.ApiRequest;
import org.selenium.pom.constants.Codes;
import org.selenium.pom.constants.EndPoint;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import java.io.IOException;
import java.util.HashMap;


public class CheckOutApi {

    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }

    private Integer orderNumber;
    private String orderDate;

    public CheckOutApi() {
    }

    public CheckOutApi(Cookies cookies) {
        this.cookies = cookies;
    }

    public Response checkout(Product product, BillingAddress billingAddress,User user) throws IOException {

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(product.getId(), 1);
        cookies = signUpApi.getCookies();
        Response checkoutPage = ApiRequest.get(cookies, EndPoint.CHECKOUT.url);

        Header header = new Header("content-type","application/x-www-form-urlencoded");
        Headers headers= new Headers(header);
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("payment_method","bacs");
        formParams.put("billing_first_name", billingAddress.getFirstName());
        formParams.put("billing_last_name",billingAddress.getLastName());
        formParams.put("billing_company",billingAddress.getCompany());
        formParams.put("billing_country", Codes.getCountryCode(billingAddress.getCountry()));
        formParams.put("billing_address_1",billingAddress.getAddressLineOne());
        formParams.put("billing_city",billingAddress.getCity());
        formParams.put("billing_state",Codes.getStateCode(billingAddress.getState()));
        formParams.put("billing_postcode",billingAddress.getPostalCode());
        formParams.put("billing_phone",billingAddress.getPhone());
        formParams.put("billing_email",billingAddress.getEmail());
        formParams.put("shipping_first_name",billingAddress.getFirstName());
        formParams.put("shipping_last_name",billingAddress.getLastName());
        formParams.put("shipping_company",billingAddress.getCompany());
        formParams.put("shipping_country",Codes.getCountryCode(billingAddress.getCountry()));
        formParams.put("shipping_address_1",billingAddress.getAddressLineOne());
        formParams.put("shipping_city",billingAddress.getCity());
        formParams.put("shipping_state",Codes.getStateCode(billingAddress.getState()));
        formParams.put("woocommerce-process-checkout-nonce",checkoutPage.htmlPath().getString(
                "**.findAll { it.@name == 'woocommerce-process-checkout-nonce' }.@value"));
        formParams.put("shipping_method[0]","flat_rate:3");
        formParams.put("_wp_http_referer", "/?wc-ajax=update_order_review");

        if (cookies == null) {
            cookies = new Cookies();
        }

        Response response = ApiRequest.post(headers,formParams,cookies,
                "/?wc-ajax=checkout");

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to add product, HTTP Status Code: " + response.getStatusCode());
        }

        /*orderNumber = response.body().jsonPath().getInt("order_id");
        String redirect = response.body().jsonPath().getString("redirect");

        Response orderReceivedPage = RestAssured.given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                cookies(cookies).
                log().all().
                when().
                get(redirect).
                then().
                log().all().
                extract().response();*/

       // Document doc = Jsoup.parse(orderReceivedPage.body().prettyPrint());
        //extractDate(doc);

        this.cookies = signUpApi.getCookies();
        return response;
    }

    private String extractDate(Document doc) {
        Element element  = doc.selectFirst(".woocommerce-order-overview__date.date");
        //String receivedOrderDate = element.text();
        //return orderDate = formatString(receivedOrderDate);
        assert element != null;
        return element.attr("value");
    }

    private String formatString(String receivedDate){
        String formatedStateTax = receivedDate.replace("Date: ","");
        return formatedStateTax;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

}
