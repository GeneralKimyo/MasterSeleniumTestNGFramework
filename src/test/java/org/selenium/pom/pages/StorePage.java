package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.constants.EndPoint;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.components.ProductThumbnail;

import java.io.IOException;

public class StorePage extends BasePage {

    private ProductThumbnail productThumbnail;

    private final By searchFld = By.id("woocommerce-product-search-field-0");
    private final By searchBtn= By.cssSelector("button[value='Search']");
    private final By searchResultTitle = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By infoTxt =  By.cssSelector(".woocommerce-info.woocommerce-no-products-found");

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public StorePage(WebDriver driver) {
        super(driver);
        productThumbnail = new ProductThumbnail(driver);
    }
    public StorePage load(){
        load(EndPoint.STORE.url);
        return this;
    }
    public StorePage search(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        return this;
    }
    public ProductPage searchExactMatch(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        return new ProductPage(driver);
    }
    private StorePage enterTextInSearchFld(String txt){
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchFld)).sendKeys(txt);
        return this;
    }

     private StorePage clickSearchBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        return this;
    }

    public String getTitle(){
        WebElement e=wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultTitle));
        return e.getText();
    }
    public ProductPage navigateToTheProduct(int id) throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(By.
                xpath("//h2[normalize-space()='"+ new Product(id).getProductName() +"']"))).click();
        return new ProductPage(driver);
    }

    public String getInfo(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(infoTxt)).getText();
    }

}
