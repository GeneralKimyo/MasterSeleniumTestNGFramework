package org.selenium.pom.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class MySelect extends BasePage {

    public MySelect(WebDriver driver) {
        super(driver);
    }

    public void getSelected(By select,String name){
        JavascriptExecutor je = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(select)).click();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='"+name+"']")));
        je.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }
}
