package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.components.MyHeader;
import org.selenium.pom.pages.components.ProductThumbnail;

import java.io.IOException;

public class Homepage extends BasePage {

    private MyHeader myHeader;
    private ProductThumbnail productThumbnail;
    public MyHeader getMyHeader() {
        return myHeader;
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }
    public Homepage(WebDriver driver) {
        super(driver);
        myHeader = new MyHeader(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    public Homepage load() {
        load("/");
        return this;
    }

    public ProductPage navigateToTheProduct(int id) throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(By.
                xpath("//h2[normalize-space()='" + new Product(id).getProductName() + "']"))).click();
        return new ProductPage(driver);
    }
}
