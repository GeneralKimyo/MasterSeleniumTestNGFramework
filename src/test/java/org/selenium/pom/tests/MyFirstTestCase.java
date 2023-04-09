package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.Homepage;
import org.selenium.pom.pages.StorePage;
import org.selenium.utils.ConfigLoader;
import org.selenium.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyFirstTestCase extends BaseTest {

    //@Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException {

        BillingAddress billingAddress = new BillingAddress(1);
        Product product = new Product(1215);
        String searchFor= "Blue";

        StorePage storePage = new Homepage(getDriver()).load().
                getMyHeader().navigateToStoreUsingMenu().
                search(searchFor);

        Assert.assertEquals(storePage.getTitle(), "Search results: “"+searchFor+"”" );
        storePage.getProductThumbnail().clickAddToCartBtn(product.getProductName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        Assert.assertEquals(cartPage.getProductName(),product.getProductName());

        CheckoutPage checkoutPage = cartPage.
                checkout().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");

    }

    //@Test
    //@Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException {

        BillingAddress billingAddress = new BillingAddress(1);
        String searchFor = "Blue";
        Product product = new Product(1215);
        User user=  new User(ConfigLoader.getInstance().getUserName(),
                ConfigLoader.getInstance().getPassword());
        StorePage storePage = new Homepage(getDriver()).load().
                getMyHeader().navigateToStoreUsingMenu().
                search(searchFor);


        Assert.assertEquals(storePage.getTitle(), "Search results: “"+searchFor+"”" );
        storePage.getProductThumbnail().clickAddToCartBtn(product.getProductName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();

        Assert.assertEquals(cartPage.getProductName(),product.getProductName());

        CheckoutPage checkoutPage = cartPage.
                checkout().
                clickHereToLogInLink().
                login(user).
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(),
                "Thank you. Your order has been received.");

    }

}

