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
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.ShippingAddress;

import java.util.HashMap;

public class ShippingApi {

    private Cookies cookies;
    public  ShippingApi(){

    }
    public ShippingApi(Cookies cookies){
        this.cookies = cookies;
    }
    public Cookies getCookies(){
        return cookies;
    }

    public Response addShippingAddress(ShippingAddress shippingAddress)
    {
        Response response = ApiRequest.get(cookies,EndPoint.CART.url);
        Header header = new Header("content-type","application/x-www-form-urlencoded");
        Headers headers= new Headers(header);
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("calc_shipping_country", Codes.getCountryCode(shippingAddress.getCountryName()));
        formParams.put("calc_shipping_city",shippingAddress.getCity());
        formParams.put("calc_shipping_state",Codes.getStateCode(shippingAddress.getStateName()));
        formParams.put("calc_shipping_postcode",shippingAddress.getPostCode());
        formParams.put("woocommerce-shipping-calculator-nonce",response.htmlPath().getString(
                "**.findAll { it.@name == 'woocommerce-shipping-calculator-nonce' }.@value"));
        formParams.put("calc_shipping","x");

        if (cookies== null){
            cookies= new Cookies();
        }
        Response response1 = ApiRequest.post(headers,formParams,cookies, EndPoint.CART.url);
        if(response1.getStatusCode()!=200)
        {
            throw new RuntimeException("Failed to edit the shipping address -" +response1.getStatusCode());
        }
        this.cookies = response1.getDetailedCookies();
        return response;
    }

    private String fetchUpdateShippingAddressNonceValueUsingJsoup(){
        Response response = getShippingAddress();
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-shipping-calculator-nonce");
        assert element != null;
        return element.attr("value");
    }

    private Response getShippingAddress(){
        Response response = ApiRequest.get(cookies,EndPoint.CART.url);
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch the address, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }
}
