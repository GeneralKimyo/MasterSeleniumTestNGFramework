package org.selenium.pom.tests;

import io.restassured.http.Cookies;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.CheckOutApi;
import org.selenium.pom.api.actions.OrderApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.AccountPage;
import org.selenium.utils.FakerUtils;
import org.selenium.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class ViewOrderTest extends BaseTest {

    @Test(description = "should be able to view orders from account page")
    public void shouldBeAbleToViewOrdersFromAccountPage() throws IOException {
        BillingAddress billingAddress = new BillingAddress(1);
        String userName= "demo"+new FakerUtils().generateRandomNumber();
        User user =  new User(userName,userName+"@askomdch.com", "demopwd");
        SignUpApi signUpApi =  new SignUpApi();
        signUpApi.register(user);
        Product product=  new Product(1215);
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(product.getId(), 1);
        OrderApi orderApi = new OrderApi(signUpApi.getCookies());
        orderApi.checkoutOrders(billingAddress);

        AccountPage accountPage = new AccountPage(getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        accountPage.load().viewOrder(orderApi.getOrderNumber());
        Assert.assertEquals(accountPage.assertOrderDetailsTitle(),"Order details");
        Assert.assertEquals(accountPage.getStatusUpdate(), "" +
                "Order #"+orderApi.getOrderNumber()+" was placed on "+orderApi.getOrderDate()+
                        " and is currently On hold.");

    }
}
