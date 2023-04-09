package org.selenium.pom.pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.constants.EndPoint;
import org.selenium.pom.objects.ShippingAddress;
import org.selenium.pom.pages.components.Computation;
import org.selenium.pom.pages.components.MyOverlay;
import org.selenium.pom.pages.components.MySelect;
import org.selenium.pom.pages.components.OrderReview;

public class CartPage extends BasePage {

    private MySelect mySelect;

    private MyOverlay myOverlay;
    private OrderReview orderReview;
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
    public Computation getComputation() {
        return computation;
    }

    public void setComputation(Computation computation) {
        this.computation = computation;
    }

    public MySelect getMySelect() {
        return mySelect;
    }

    public void setMySelect(MySelect mySelect) {
        this.mySelect = mySelect;
    }

    //private final By productName = By.cssSelector("td[class='product-name'] a");
    //private final By checkOutBtn = By.cssSelector(".checkout-button");
    @FindBy(css = "td[class='product-name'] a") private WebElement productName;
    @FindBy(css = ".checkout-button") private WebElement checkOutBtn;

    private final By couponCodeFld =  By.cssSelector("#coupon_code");
    private final By couponApplyBtn =  By.cssSelector("button[value='Apply coupon']");
    private final By alertTxt = By.cssSelector("div[role='alert']");
    private final By total = By.xpath("//td[@data-title='Total']/child::strong/child::span/child::bdi");
    private final By countryComboBox = By.xpath("//span[@id='select2-calc_shipping_country-container']");
    private final By stateComboBox = By.xpath("//span[@id='select2-calc_shipping_state-container']");
    private  final By cityFld = By.cssSelector("#calc_shipping_city");
    private  final By postCodeFld = By.cssSelector("#calc_shipping_postcode");
    private final By changeAddressLinkTxt =  By.xpath("//a[normalize-space()='Change address']");
    private final By updateAddressBtn =  By.xpath("//button[normalize-space()='Update']");


    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        mySelect =  new MySelect(driver);
        myOverlay=  new MyOverlay(driver);
        computation =  new Computation(driver);
        orderReview =  new OrderReview(driver);
    }
    public CartPage load(){
        load(EndPoint.CART.url);
        return this;
    }

    public String getProductName(){
       return productName.getText();
    }
    public CheckoutPage checkout(){
        checkOutBtn.click();
        return new CheckoutPage(driver);
    }
    public CartPage enterCouponCode(String couponCode){
       wait.until(ExpectedConditions.visibilityOfElementLocated(couponCodeFld)).sendKeys(couponCode);
       return this;
    }
    public CartPage applyCouponCode(String couponCode){
        enterCouponCode(couponCode);
        wait.until(ExpectedConditions.elementToBeClickable(couponApplyBtn)).click();
        return this;
    }
    public String successNotice(){
        waitForOverlaysToDisappear(myOverlay.getOverlay());
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertTxt)).getText();
    }
    public double getSubTotal(){
        waitForOverlaysToDisappear(myOverlay.getOverlay());
        return getOrderReview().getSubTotal();
    }
    public double getActualTotal(String couponCode,double discount) throws Exception {
        computation.getActualTotal(couponCode,discount, getSubTotal());
        return computation.getTotal();
    }
    public double getTotal(){
        waitForOverlaysToDisappear(myOverlay.getOverlay());
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(total));
        return Double.valueOf(e.getText().replace("$",""));
    }
    public CartPage clickChangeAddress() throws InterruptedException {
        waitForOverlaysToDisappear(myOverlay.getOverlay());
         wait.until(ExpectedConditions.visibilityOfElementLocated(changeAddressLinkTxt)).click();
         Thread.sleep(5000);
        return this;
    }
    public CartPage selectCountry(String countryName){
        getMySelect().getSelected(countryComboBox, countryName);
        return this;
    }
    public CartPage selectState(String stateName){
        getMySelect().getSelected(stateComboBox,stateName);
        return this;
    }
    public CartPage enterCity(String city)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityFld)).sendKeys(city);
        return this;
    }
    public CartPage enterPostCode(String postCode)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(postCodeFld)).sendKeys(postCode);
        return this;
    }
    public CartPage setShippingAddress(ShippingAddress shippingAddress){
                selectCountry(shippingAddress.getCountryName()).
                selectState(shippingAddress.getStateName()).
                enterCity(shippingAddress.getCity()).
                enterPostCode(shippingAddress.getPostCode());
                return this;
    }
    public CartPage clickUpdateBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(updateAddressBtn)).click();
        return this;
    }
    public CartPage waitForUpdateBtnToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateAddressBtn));
        return this;
    }

    public CartPage updateShippingAddress(){
                clickUpdateBtn().
                waitForUpdateBtnToDisappear();
        return this;
    }
}
