package org.selenium.pom.constants;

public enum Coupons {
    FREESHIP("freeship"),
    OFFCART5("offcart5"),
    OFF25("off25");
    public final String couponCode;
    Coupons(String couponCode){
        this.couponCode = couponCode;
    }
}
