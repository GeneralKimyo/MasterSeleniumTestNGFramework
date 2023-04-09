package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.Homepage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class NavigationTest extends BaseTest {

    @Test
    public void navigateFromHomeToStoreUsingMenuLink(){
        StorePage storePage = new Homepage(getDriver()).load().
                getMyHeader().navigateToStoreUsingMenu();
        Assert.assertEquals(storePage.getTitle(), "Store" );
    }
    @Test
    public void navigateFromStoreToProductPage() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage= new StorePage(getDriver()).
                load().
                navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle(),product.getProductName());
    }
    @Test
    public void navigateFromHomeToFeaturedProductPage() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage = new Homepage(getDriver()).
                load().
                navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle(),product.getProductName());
    }

}
