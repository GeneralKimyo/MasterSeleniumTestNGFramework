package org.selenium.pom.tests;

import com.opencsv.exceptions.CsvValidationException;
import io.qameta.allure.*;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataproviders.MyDataProvider;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.USTaxRates;
import org.selenium.pom.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
@Epic("Checkout")
@Feature("Tax Rate")
public class TaxRateTest extends BaseTest {

    /*@Link("https://qameta.io")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")*/
    @Severity(SeverityLevel.CRITICAL)
    @Story("Validate computation of tax rate")
    @Description("This test is to validate if the calculated tax associated with country based in US is equal to the expected tax")
    @Test(dataProvider = "getBillingAddressInUSRegion", dataProviderClass = MyDataProvider.class,
            description = "validate tax rate in US")
    public void validateTaxRateInUS(BillingAddress billingAddress) throws IOException, CsvValidationException {
        Product product = new Product(1215);
        CartApi cartApi = new CartApi();
        cartApi.addToCart(product.getId(), 1);
        CheckoutPage checkoutPage=  new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load().
                setBillingAddress(billingAddress);
        //System.out.println("The state is "+billingAddress.getState());
        USTaxRates usTaxRates = new USTaxRates();
        usTaxRates.getTaxRates("src/test/resources/us_tax_rates.csv",
                billingAddress.getState());
        //System.out.println("The tax rate is "+ usTaxRates.getTaxRate());
        //System.out.println("The statecode is "+ Codes.getStateCode(billingAddress.getState()));
        Assert.assertEquals("$"+checkoutPage.getComputation().
                getActualTaxAmount(usTaxRates.getTaxRate(),checkoutPage.getSubTotal()),
                "$"+checkoutPage.getTaxAmount());
    }
}
