package org.selenium.pom.tests;

import org.selenium.pom.api.actions.BillingApi;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest {
    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress billingAddress = new BillingAddress(1);
        CheckoutPage checkoutPage= new CheckoutPage(getDriver()).
                load();
        CartApi cartApi= new CartApi();
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");
    }
    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress billingAddress = new BillingAddress(1);
        String username = "demouser" +new FakerUtils().generateRandomNumber();
        User user= new User(username,username + "@askomdch.com","demopw");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi= new CartApi(signUpApi.getCookies());
        Product product= new Product(1215);
        cartApi.addToCart(product.getId(),1);

        CheckoutPage checkoutPage =  new CheckoutPage(getDriver()).
                load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");
    }
    @Test
    public void guestCheckoutUsingCashOnDelivery() throws Exception {
        BillingAddress billingAddress = new BillingAddress(1);
        CheckoutPage checkoutPage= new CheckoutPage(getDriver()).
                load();
        CartApi cartApi= new CartApi();
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectCashOnDelivery().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");
    }
    @Test
    public void loginAndCheckoutUsingCashOnDelivery() throws Exception {
        BillingAddress billingAddress = new BillingAddress(1);
        String username = "demouser" +new FakerUtils().generateRandomNumber();
        User user= new User(username,username + "@askomdch.com","demopw");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi= new CartApi(signUpApi.getCookies());
        Product product= new Product(1215);
        cartApi.addToCart(product.getId(),1);

        CheckoutPage checkoutPage =  new CheckoutPage(getDriver()).
                load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load().
                setBillingAddress(billingAddress).
                selectCashOnDelivery().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");
    }

    //@Test
    public void guestCheckoutWithCreateAccount() throws IOException {
        BillingAddress billingAddress = new BillingAddress(1);
        String userName = "demouser" + new FakerUtils().generateRandomNumber();
        User user =  new User(userName,"demopwd");
        CheckoutPage checkoutPage= new CheckoutPage(getDriver()).
                load();
        CartApi cartApi= new CartApi();
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                createAccount(user.getUserName(),user.getPassword()).
                selectDirectBankTransfer().
                placeOrder();
        SignUpApi signUpApi=new SignUpApi();
        signUpApi.register(user);
        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");
    }
    @Test
    public void checkoutWithAnAccountHavingBillingAddress() throws IOException {
        BillingAddress billingAddress = new BillingAddress(1);
        String username = "demouser" +new FakerUtils().generateRandomNumber();
        User user= new User(username,username + "@askomdch.com","demopw");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        BillingApi billingApi =  new BillingApi(signUpApi.getCookies());
        billingApi.addBillingAddress(billingAddress);
        CartApi cartApi= new CartApi(signUpApi.getCookies());
        Product product= new Product(1215);
        cartApi.addToCart(product.getId(),1);

        CheckoutPage checkoutPage =  new CheckoutPage(getDriver()).
                load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.
                load();
        checkoutPage.selectCashOnDelivery().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");
    }
}
