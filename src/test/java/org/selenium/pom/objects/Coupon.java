package org.selenium.pom.objects;

import org.selenium.utils.JacksonUtils;

import java.io.IOException;

public class Coupon {

    private String couponCode;
    private boolean usRegion;
    private double discount;
    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public boolean isUsRegion() {
        return usRegion;
    }

    public void setUsRegion(boolean usRegion) {
        this.usRegion = usRegion;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Coupon(){}
    public Coupon(String couponCode) throws IOException {
        Coupon[] coupons = JacksonUtils.deserializeJson("products.json",Coupon[].class);
        for(Coupon coupon: coupons ){
            if(coupon.getCouponCode() == couponCode){
                this.couponCode =  coupon.getCouponCode();
                this.usRegion =  coupon.isUsRegion();
                this.discount =  coupon.getDiscount();
            }
        }
    }
}
