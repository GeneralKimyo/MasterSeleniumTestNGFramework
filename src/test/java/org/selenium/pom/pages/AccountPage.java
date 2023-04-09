package org.selenium.pom.pages;

import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.constants.EndPoint;

public class AccountPage extends BasePage {

    private final By loginTitle =  By.xpath("//h2[normalize-space()='Login']");
    private final By userNameFld = By.cssSelector("#username");
    private final By passwordFld = By.cssSelector("#password");
    private final By loginBtn = By.cssSelector("button[value='Log in']");
    private final By errorTxt = By.xpath("//ul[@class='woocommerce-error']/child::li");
    private final By orderMenuLink = By.cssSelector("a[href='https://askomdch.com/account/orders/']");
    private final By viewBtn =  By.xpath("//*[contains(@href,'50093')][text()='View']");
    private final By orderDetailsTitle = By.cssSelector(".woocommerce-order-details__title");
    private  final By statusTxt= By.xpath("//div[@class='woocommerce-MyAccount-content']/child::p");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage load(){
        load("/account/orders/");
        return this;
    }

    public String getLoginTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginTitle)).getText();
    }
    public AccountPage enterUserName(String userName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameFld)).sendKeys(userName);
        return this;
    }
    public AccountPage enterPassword(String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        return this;
    }
    public AccountPage login(String userName , String password){
        enterUserName(userName).enterPassword(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }
    public String errorMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorTxt)).getText();
    }
    public AccountPage navigateOrdersFromAccount(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderMenuLink)).click();
        return this;
    }
    public AccountPage viewOrder(String orderNumber){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[contains(@href,'"+orderNumber+"')][text()='View']"))).click();
        return this;
    }
    public String assertOrderDetailsTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderDetailsTitle)).getText();
    }

    public String getStatusUpdate(){
        return  wait.until(ExpectedConditions.visibilityOfElementLocated(statusTxt)).getText();
    }
}
