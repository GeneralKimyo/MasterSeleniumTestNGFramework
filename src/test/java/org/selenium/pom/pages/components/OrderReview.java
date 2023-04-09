package org.selenium.pom.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class OrderReview extends BasePage {
    public OrderReview(WebDriver driver) {
        super(driver);
    }

    private final By subTotal = By.xpath(
            "//tr[@class='cart-subtotal']/child::td/child::span/child::bdi");
    private final By taxAmount = By.xpath("//*[contains(@class,'tax-rate')]/child::td/child::span");
    public double getSubTotal()
    {
        WebElement e = driver.findElement(subTotal);
        return Double.valueOf(e.getText().replace("$", ""));
    }
    public double getTaxAmount(){
        WebElement e= wait.until(ExpectedConditions.visibilityOfElementLocated(taxAmount));
        return Double.valueOf(e.getText().replace("$",""));
    }
}
