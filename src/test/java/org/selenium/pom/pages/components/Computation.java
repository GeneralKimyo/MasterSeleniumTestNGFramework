package org.selenium.pom.pages.components;

import org.openqa.selenium.WebDriver;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.constants.Coupons;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Computation extends BasePage {
    public Computation(WebDriver driver) {
        super(driver);
    }
    private double total;
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void getActualTotal(String couponCode, double discount,double subTotal) throws IOException {

        if(couponCode.equals(Coupons.FREESHIP.couponCode)){
            total = (subTotal+10) - discount;
        }
        if(couponCode.equals(Coupons.OFFCART5.couponCode)){
            total =  (subTotal +10) - discount;
        }
        if(couponCode.equals(Coupons.OFF25.couponCode)){
            total= (subTotal+10)-(subTotal * discount);
        }
    }
    public double getActualTaxAmount(String taxRate,double subTotal){
        double tax=subTotal * (Double.valueOf(taxRate.replace("%",""))/100);
        DecimalFormat decimalFormat= new DecimalFormat("#.0#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(decimalFormat.format(tax));
    }
}
