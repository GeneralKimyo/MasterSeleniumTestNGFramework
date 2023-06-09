package org.selenium.pom.api.actions;

import io.qameta.allure.Step;
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


public class BillingApi {
    private Cookies cookies;
    public BillingApi(){

    }
    public BillingApi(Cookies cookies){
        this.cookies = cookies;
    }
    public Cookies getCookies(){
        return cookies;
    }

    @Step("Injecting billing address cookies using Billing API")
    public Response addBillingAddress(BillingAddress billingAddress)
    {
        Header header = new Header("content-type","application/x-www-form-urlencoded");
        Headers headers= new Headers(header);
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("billing_first_name", billingAddress.getFirstName());
        formParams.put("billing_last_name",billingAddress.getLastName());
        formParams.put("billing_country", Codes.getCountryCode(billingAddress.getCountry()));
        formParams.put("billing_address_1",billingAddress.getAddressLineOne());
        formParams.put("billing_city",billingAddress.getCity());
        formParams.put("billing_state",Codes.getStateCode(billingAddress.getState()));
        formParams.put("billing_postcode",billingAddress.getPostalCode());
        formParams.put("billing_company",billingAddress.getCompany());
        formParams.put("billing_phone",billingAddress.getPhone());
        formParams.put("woocommerce-edit-address-nonce",fetchEditBillingAddressNonceValueUsingJsoup());
        formParams.put("action","edit_address");
        formParams.put("save_address","Save address");
        formParams.put("billing_email",billingAddress.getEmail());
        if (cookies== null){
            cookies= new Cookies();
        }
        Response response = ApiRequest.post(headers,formParams,cookies,EndPoint.ACCOUNT_EDIT_BILLING_ADDRESS.url);
        if(response.getStatusCode()!=302)
        {
            throw new RuntimeException("Failed to edit the address of the account -" +response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }

    private String fetchEditBillingAddressNonceValueUsingJsoup(){
        Response response = getBillingAddress();
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-edit-address-nonce");
        assert element != null;
        return element.attr("value");
    }

    private Response getBillingAddress(){
        Response response = ApiRequest.get(cookies,EndPoint.ACCOUNT_EDIT_BILLING_ADDRESS.url);
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }
}
