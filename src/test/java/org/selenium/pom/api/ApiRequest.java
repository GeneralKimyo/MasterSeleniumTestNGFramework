package org.selenium.pom.api;

import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.selenium.utils.ConfigLoader;
import java.util.HashMap;

import static io.restassured.RestAssured.given;


public class ApiRequest extends SpecBuilder {

    public static Response post(Headers headers, HashMap<String,Object> formParams, Cookies cookies, String endpoint){
        return given(getRequestSpec()).
                    headers(headers).
                    formParams(formParams).
                    cookies(cookies).
                when().
                    post(endpoint).
                then().spec(getResponseSpec()).
                    extract().
                    response();
    }
    public static Response get(Cookies cookies, String endpoint){
        return given(getRequestSpec()).
                    baseUri(ConfigLoader.getInstance().getBaseUrl()).
                    cookies(cookies).
                when().
                    get(endpoint).
                then().spec(getResponseSpec()).
                    extract().
                    response();
    }
}
