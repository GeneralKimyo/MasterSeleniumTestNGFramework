package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataproviders.MyDataProvider;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.Homepage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class addToCartTest extends BaseTest {
    @Test
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage  =  new StorePage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getProductName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(),product.getProductName());
    }

    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class)
    public void addFeaturedProductToCart(Product product) throws IOException {
        //Product product = new Product(1215);
        CartPage cartPage  =  new Homepage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getProductName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(),product.getProductName());
    }

    @Test
    public void addToCartFromProductPage() throws IOException {
        Product product = new Product(1215);
        String productNameSeparatedByDash = product.getProductName().toLowerCase().replaceAll(" ","-");
        ProductPage productPage  =  new ProductPage(getDriver()).
                loadProduct(productNameSeparatedByDash).
                clickAddToCartBtn();
        Assert.assertTrue(productPage.getAlert().contains("“"+product.getProductName()+"” has been added to your cart."));
        CartPage cartPage = new ProductPage(getDriver()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(),product.getProductName());
    }

}
