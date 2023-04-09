package org.selenium.pom.dataproviders;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Coupon;
import org.selenium.pom.objects.Product;
import org.selenium.utils.JacksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyDataProvider extends BaseTest {


    @DataProvider(parallel = true)
    public Object[] getFeaturedProducts() throws IOException {
        Product[] products = JacksonUtils.deserializeJson("products.json",Product[].class);
        List<Product> featuredProductArrayList =  new ArrayList<>();
        for(Product product : products ){
            if( product.isFeatured()){
                featuredProductArrayList.add(product);
            }
        }
        return featuredProductArrayList.toArray();
    }
    @DataProvider(parallel = true)
    public Object[] addCouponCodeForNonUSRegion() throws IOException {
       Coupon[] coupons = JacksonUtils.deserializeJson("coupons.json",Coupon[].class);
        List<Coupon> couponForNonUSRegionArrayList =  new ArrayList<>();
        for(Coupon coupon : coupons ){
            if(!coupon.isUsRegion()){
                couponForNonUSRegionArrayList.add(coupon);
            }
        }
        return couponForNonUSRegionArrayList.toArray();
    }

    @DataProvider(parallel = true)
    public Object[] getBillingAddressInUSRegion() throws IOException {
        BillingAddress[] billingAddresses = JacksonUtils.deserializeJson("myBillingAddress.json",BillingAddress[].class);
        List<BillingAddress> billingAddressesInUsRegionArrayList =  new ArrayList<>();
        for(BillingAddress billingAddress : billingAddresses ){
            if(billingAddress.isUsRegion()){
                billingAddressesInUsRegionArrayList.add(billingAddress);
            }
        }
        return billingAddressesInUsRegionArrayList.toArray();
    }
}
