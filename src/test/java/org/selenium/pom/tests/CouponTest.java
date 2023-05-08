package org.selenium.pom.tests;

import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.ShippingApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataproviders.MyDataProvider;
import org.selenium.pom.objects.Coupon;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.ShippingAddress;
import org.selenium.pom.pages.CartPage;
import org.selenium.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CouponTest extends BaseTest {
    @Test(dataProvider = "addCouponCodeForNonUSRegion", dataProviderClass = MyDataProvider.class)
    public void addCouponCodeForNonUSRegion(Coupon coupon) throws Exception {
        ShippingAddress shippingAddress = JacksonUtils.deserializeJson(
                "myShippingAddress.json",ShippingAddress.class);
        Product product = new Product(1215);
        CartApi cartApi =  new CartApi();
        cartApi.addToCart(product.getId(), 1);
        ShippingApi shippingApi = new ShippingApi(cartApi.getCookies());
        shippingApi.addShippingAddress(shippingAddress);

        CartPage cartPage  =  new CartPage(getDriver()).load();
        injectCookiesToBrowser(cartApi.getCookies());
        cartPage.load().
                applyCouponCode(coupon.getCouponCode());
        Assert.assertEquals(cartPage.successNotice(),"Coupon code applied successfully.");

        cartPage.getComputation().getActualTotal(coupon.getCouponCode(),coupon.getDiscount(), cartPage.getSubTotal());
        System.out.println("The subtotal is: "+cartPage.getSubTotal() +"The total is: "+cartPage.getComputation().getTotal() +coupon.getCouponCode()+
                "  The Actual Total is :  "+cartPage.getTotal());
        Assert.assertEquals("$"+ cartPage.getActualTotal(coupon.getCouponCode(), coupon.getDiscount())
                ,"$"+cartPage.getTotal());
    }
}
