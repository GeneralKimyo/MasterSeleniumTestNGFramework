package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.api.ApiRequest;
import org.selenium.pom.constants.EndPoint;
import org.selenium.pom.objects.User;
import org.selenium.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SignUpApi {
    public SignUpApi(){

    }
    public SignUpApi(Cookies cookies){
        this.cookies =  cookies;
    }
    private Cookies cookies;


    public Cookies getCookies(){
        return cookies;
    }
    public String fetchRegisterNonceValueUsingGroovy(){
        Response response = getAccount();
        return response.htmlPath().getString(
                "**.findAll {it.@name=='woocommerce-register-nonce'}.@value");
    }
    public String fetchRegisterNonceValueUsingJSoup(){
        Response response = getAccount();
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-register-nonce");
        return element.attr("value");
    }
    private Response getAccount(){

        Response response = ApiRequest.get(new Cookies(), EndPoint.ACCOUNT.url);
        if(response.getStatusCode()!=200)
        {
            throw new RuntimeException(
                    "Failed to fetch the account, HTTP Status Code: " +response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }
    public Response register(User user){

        Header header =  new Header("content-type","application/x-www-form-urlencoded");
        Headers headers =  new Headers(header);
        HashMap<String,Object> formParams= new HashMap();
        formParams.put("username", user.getUserName());
        formParams.put("email", user.getEmail());
        formParams.put("password",user.getPassword());
        formParams.put("woocommerce-register-nonce",fetchRegisterNonceValueUsingGroovy());
        formParams.put("register","Register");
        Response response = ApiRequest.post(headers,formParams,cookies,EndPoint.ACCOUNT.url);
        if(response.getStatusCode()!=302)
        {
            throw new RuntimeException(
                    "Failed to register the account, HTTP Status Code: " +response.getStatusCode());
        }
        this.cookies =  response.getDetailedCookies();
        return response;
    }
}
