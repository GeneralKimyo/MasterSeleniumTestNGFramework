package org.selenium.pom.tests;

import io.qameta.allure.*;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.AccountPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Askomdch.com Oauth 2.0")
@Feature("SignUp Api")
public class LogInTest extends BaseTest {
    @Test
    public void logInDuringCheckout() throws IOException, InterruptedException {
        String username = "demouser" +new FakerUtils().generateRandomNumber();
        User user= new User(username,username + "@askomdch.com","demopw");
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi= new CartApi();
        Product product= new Product(1215);
        cartApi.addToCart(product.getId(),1);

        CheckoutPage checkoutPage =  new CheckoutPage(getDriver()).
                load();
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load();
                checkoutPage.clickHereToLogInLink().
                login(user);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getProductName()));
    }
    @Story("Log in story")
    @Test
    public void  shouldNotLogInWithInvalidPassword(){
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User(username,username + "@askomdch.com","demopwd");
        SignUpApi signUpApi =  new SignUpApi();
        signUpApi.register(user);

        AccountPage accountPage =  new AccountPage(getDriver()).
                load();
        Assert.assertEquals(accountPage.getLoginTitle(),"Login");
        accountPage.login(user.getUserName(),"invalidPassword");
        Assert.assertEquals(accountPage.errorMessage(),
                "Error: The password you entered for the username "+user.getUserName()+
                        " is incorrect. Lost your password?");
    }

    @Story("Log in story")
    @Description("This will trigger error when log in with non existing username.")
    @Test(description = "should not log in with non existing username")
    public void  shouldNotLogInWithNonExistingUserName(){
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User(username,username + "@askomdch.com","demopwd");

        AccountPage accountPage =  new AccountPage(getDriver()).
                load();
        Assert.assertEquals(accountPage.getLoginTitle(),"Login");
        accountPage.login(user.getUserName(), user.getPassword());
        Assert.assertEquals(accountPage.errorMessage(),
                "Error: The username "+user.getUserName()+
                        " is not registered on this site. If you are unsure of your username,"+
                        " try your email address instead.");
    }
}
