package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.selenium.pom.api.ApiRequest;
import org.selenium.pom.constants.EndPoint;

import java.util.HashMap;

public class CartApi {
    private Cookies cookies;
    public CartApi(){}
    public CartApi(Cookies cookies)
    {
        this.cookies = cookies;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public Response addToCart(int productId, int quantity){
        Header header =  new Header("content-type","application/x-www-form-urlencoded");
        Headers headers =  new Headers(header);
        HashMap<String,Object> formParams= new HashMap();
        formParams.put("product_sku","");
        formParams.put("product_id", productId);
        formParams.put("quantity", quantity);
        if (cookies== null){
            cookies= new Cookies();
        }
        Response response = ApiRequest.post(headers,formParams,cookies, EndPoint.ADD_TO_CART.url);
        if(response.getStatusCode()!=200)
        {
            throw new RuntimeException(
                    "Failed to add product" + productId+"to cart, HTTP Status Code: " +response.getStatusCode());
        }
        this.cookies =  response.getDetailedCookies();
        return response;
    }

}
