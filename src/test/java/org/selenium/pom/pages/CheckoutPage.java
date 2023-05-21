package org.selenium.pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.constants.EndPoint;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.components.Computation;
import org.selenium.pom.pages.components.MyOverlay;
import org.selenium.pom.pages.components.MySelect;
import org.selenium.pom.pages.components.OrderReview;

import java.util.List;


public class CheckoutPage extends BasePage {

    private final By firstNameFld =By.id("billing_first_name");
    private final By lastNameFld= By.id("billing_last_name");
    private final By billingCompanyFld = By.cssSelector("#billing_company");
    private final By countryDropDown = By.id("billing_country");
    private final By alternateCountryDropDown = By.id("select2-billing_country-container");
    private final By addressLineOneFLd = By.id("billing_address_1");
    private final By billingCityFld = By.id("billing_city");
    private final By stateDropDown = By.id("billing_state");
    private final By alternateStateDropDown = By.id("select2-billing_state-container");
    private final By billingPostCodeFld = By.id("billing_postcode");
    private  final By billingPhoneFld = By.cssSelector("#billing_phone");
    private final By billingEmailFld = By.id("billing_email");

    private final By paymentMethod = By.cssSelector(".wc_payment_methods.payment_methods.methods");
    private final By directBankTransferRadioBtn =  By.id("payment_method_bacs");
    private final By cashOnDeliveryRadioBtn = By.id("payment_method_cod");
    private final By placeOrderBtn = By.id("place_order");
    private final  By successNotice = By.cssSelector(".woocommerce-notice");

    private final By clickHereToLogInLink = By.className("showlogin");
    private final By usernameFld = By.id("username");
    private final By passwordFld = By.id("password");
    private final By loginBtn = By.name("login");
    private final By createAccountCheckBox = By.cssSelector("#createaccount");
    private final By createAccountUserNameFld =  By.cssSelector("#account_username");
    private final By createAccountPasswordFld = By.cssSelector("#account_password");
    private final By errorTxt = By.xpath("//ul[@class='woocommerce-error']/child::li");
    private final By productName = By.cssSelector("td[class='product-name']>a");
    private MySelect mySelect;
    private OrderReview orderReview;
    private MyOverlay myOverlay;
    private Computation computation;
    public MyOverlay getMyOverlay() {
        return myOverlay;
    }
    public void setMyOverlay(MyOverlay myOverlay) {
        this.myOverlay = myOverlay;
    }
    public OrderReview getOrderReview() {
        return orderReview;
    }
    public void setOrderReview(OrderReview orderReview) {
        this.orderReview = orderReview;
    }
    public MySelect getMySelect() {
        return mySelect;
    }
    public void setMySelect(MySelect mySelect) {
        this.mySelect = mySelect;
    }
    public Computation getComputation() {
        return computation;
    }

    public void setComputation(Computation computation) {
        this.computation = computation;
    }

    public CheckoutPage(WebDriver driver) {
        super(driver);
        mySelect = new MySelect(driver);
        myOverlay =  new MyOverlay(driver);
        orderReview =  new OrderReview(driver);
        computation =  new Computation(driver);
    }
    @Step("I'm on checkout page")
    public CheckoutPage load(){
        load(EndPoint.CHECKOUT.url);
        return this;
    }
    @Step("I clicked the log in link")
    public CheckoutPage clickHereToLogInLink(){
        wait.until(ExpectedConditions.elementToBeClickable(clickHereToLogInLink)).click();
        return this;
    }
    @Step("I entered a username")
    public CheckoutPage enterUserName(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        return this;
    }
    @Step("I entered a password")
    public CheckoutPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        return this;
    }
    @Step("I clicked the log in button")
    public CheckoutPage clickLogInBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }
    public CheckoutPage waitForLogInBtnToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginBtn));
        return this;
    }
    public CheckoutPage login(User user) {
        enterUserName(user.getUserName());
        enterPassword(user.getPassword());
        clickLogInBtn().waitForLogInBtnToDisappear();
        return this;
    }
    @Step("I entered first name")
    public CheckoutPage enterFirstName(String firstName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameFld)).clear();
        driver.findElement(firstNameFld).sendKeys(firstName);
        return this;
    }
    @Step("I entered last name")
    public CheckoutPage enterLastName(String lastName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameFld)).clear();
        driver.findElement(lastNameFld).sendKeys(lastName);
        return this;
    }
    @Step("I selected country")
    public CheckoutPage selectCountry(String countryName){
        //Select select = new Select(driver.findElement(countryDropDown));
        //select.selectByVisibleText(countryName);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.
                elementToBeClickable(alternateCountryDropDown)).click();
        //WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[id$='PH']")));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='"+countryName+"']")));
        System.out.println(element.getText());
        //je.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
        return this;
    }
    @Step("I entered address line one")
    public CheckoutPage enterAddressLineOne (String addressOne){
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOneFLd)).clear();
        driver.findElement(addressLineOneFLd).sendKeys(addressOne);
        return this;
    }
    @Step("I entered city")
    public CheckoutPage enterCity(String City){
        wait.until(ExpectedConditions.visibilityOfElementLocated(billingCityFld)).clear();
        driver.findElement(billingCityFld).sendKeys(City);
        return this;
    }
    @Step("I selected state")
    public CheckoutPage selectState(String stateName){
        //Select select = new Select(driver.findElement(stateDropDown));
        //select.selectByVisibleText(stateName);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.
                elementToBeClickable(alternateStateDropDown)).click();
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.
                xpath("//li[text()='"+stateName+"']")));
        je.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
        return this;
    }
    @Step("I entered postal code")
    public CheckoutPage enterPostCode(String PostCode){
        wait.until(ExpectedConditions.visibilityOfElementLocated(billingPostCodeFld)).clear();
        driver.findElement(billingPostCodeFld).sendKeys(PostCode);
        return this;
    }
    @Step("I entered email")
    public CheckoutPage enterEmail(String Email){
        wait.until(ExpectedConditions.visibilityOfElementLocated(billingEmailFld)).clear();
        driver.findElement(billingEmailFld).sendKeys(Email);
        return this;
    }

    public CheckoutPage setBillingAddress(BillingAddress billingAddress){

        return  enterFirstName(billingAddress.getFirstName()).
                enterLastName(billingAddress.getLastName()).
                selectCountry(billingAddress.getCountry()).
                enterAddressLineOne(billingAddress.getAddressLineOne()).
                enterCity(billingAddress.getCity()).
                selectState(billingAddress.getState()).
                enterPostCode(billingAddress.getPostalCode()).
                enterEmail(billingAddress.getEmail());
    }

    public double getSubTotal(){
        waitForOverlaysToDisappear(myOverlay.getOverlay());
        return orderReview.getSubTotal();
    }

    public double getTaxAmount(){
        waitForOverlaysToDisappear(myOverlay.getOverlay());
        return orderReview.getTaxAmount();
    }
    public  CheckoutPage selectDirectBankTransfer(){
        waitForOverlaysToDisappear(myOverlay.getOverlay());
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioBtn));
        if(!e.isSelected()){
            e.click();
        }
        return this;
    }

    public CheckoutPage selectCashOnDelivery() {
        //waitForOverlaysToDisappear(By.cssSelector(".blockUI.blockOverlay"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        WebElement codBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(cashOnDeliveryRadioBtn));
        je.executeScript("arguments[0].click();", codBtn);
        return this;
    }

    public CheckoutPage placeOrder(){
        waitForOverlaysToDisappear(myOverlay.getOverlay());
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
        return this;
    }
    public CheckoutPage createAccount(String userName, String password){
        wait.until(ExpectedConditions.elementToBeClickable(createAccountCheckBox)).click();
        enterCreateAccountUserName(userName).enterCreateAccountPassword(password);
        return this;
    }
    public CheckoutPage cancelCreateAccount(){
       WebElement e = wait.until(ExpectedConditions.elementToBeClickable(createAccountCheckBox));
       if(e.isSelected()){
           e.click();
       }
        return this;
    }
    public CheckoutPage enterCreateAccountUserName(String userName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountUserNameFld)).sendKeys(userName);
        return this;
    }
    public CheckoutPage enterCreateAccountPassword(String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountPasswordFld)).sendKeys(password);
        return this;
    }
    public String getNotice(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice)).getText();
    }
    public String getProductName(){
        /*int i= 5;
        while(i>0){
            try{
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();}
        catch (StaleElementReferenceException e){
                System.out.println("NOT FOUND , TRY AGAIN" +e);
        }
        Thread.sleep(5000);
        i--;
        }
        throw new Exception("Element not found");*/
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td[@class='product-name'])[1]"))).getText();
    }

}
