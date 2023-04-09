package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.api.ApiRequest;
import org.selenium.pom.constants.Codes;
import org.selenium.pom.constants.EndPoint;
import org.selenium.pom.objects.BillingAddress;


import java.util.HashMap;

public class OrderApi {
    private Cookies cookies;
    private String orderNumber;
    private String orderDate;

    private String redirect;

    public OrderApi(){
    }
    public OrderApi(Cookies cookies){
        this.cookies = cookies;
    }
    public Cookies getCookies(){
        return cookies;
    }
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return extractDate();
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;

    }
    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Response checkoutOrders(BillingAddress billingAddress)
    {
        Header header = new Header("content-type","application/x-www-form-urlencoded");
        Headers headers= new Headers(header);
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("payment_method","bacs");
        formParams.put("billing_first_name", billingAddress.getFirstName());
        formParams.put("billing_last_name",billingAddress.getLastName());
        formParams.put("billing_company",billingAddress.getCompany());
        formParams.put("billing_country", Codes.getCountryCode(billingAddress.getCountry()));
        formParams.put("billing_address_1",billingAddress.getAddressLineOne());
        formParams.put("billing_address_2","");
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
        formParams.put("shipping_address_2","");
        formParams.put("shipping_city",billingAddress.getCity());
        formParams.put("shipping_state",Codes.getStateCode(billingAddress.getState()));
        formParams.put("shipping_postcode",billingAddress.getPostalCode());
        formParams.put("order_comments","");
        formParams.put("woocommerce-process-checkout-nonce",fetchCheckoutNonceValueUsingJsoup());
        formParams.put("shipping_method[0]","flat_rate:3");
        formParams.put("_wp_http_referer", "/?wc-ajax=update_order_review");

        if (cookies== null){
            cookies= new Cookies();
        }
        Response response = ApiRequest.post(headers,formParams,cookies, "/?wc-ajax=checkout");
        if(response.getStatusCode()!=200)
        {
            throw new RuntimeException("Failed to checkout " +response.getStatusCode());
        }

        this.orderNumber = response.body().jsonPath().getString("order_id");
        this.redirect = response.body().jsonPath().getString("redirect");

        this.cookies = response.getDetailedCookies();
        return response;
    }

    private String fetchCheckoutNonceValueUsingJsoup(){
        Response response = getOrders();
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-process-checkout-nonce");
        assert element != null;
        return element.attr("value");
    }

    private Response getOrders(){
        Response response = ApiRequest.get(cookies,EndPoint.CHECKOUT.url);
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch the orders, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }
    private String extractDate() {
        Response orderReceivedPage = ApiRequest.get(cookies, redirect);
        Document doc = Jsoup.parse(orderReceivedPage.body().prettyPrint());
        Element element = doc.selectFirst(".woocommerce-order-overview__date.date");
        return element.text().replace("Date: ", "");
    }

}
