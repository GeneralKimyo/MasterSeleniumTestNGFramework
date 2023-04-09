package org.selenium.pom.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.base.BasePage;

public class MyOverlay extends BasePage {
    public MyOverlay(WebDriver driver) {
        super(driver);
    }

    public By getOverlay() {
        return overlay;
    }
    private final By overlay = By.cssSelector(".blockUI.blockOverlay");

}
