package org.selenium.pom.constants;

public enum EndPoint {
    STORE("/store"),
    ACCOUNT("/account"),
    EDIT_ACCOUNT("account/edit-address/"),
    ADD_TO_CART("/?wc-ajax=add_to_cart"),
    CART("/cart/"),
    CHECKOUT("/checkout"),
    ACCOUNT_EDIT_BILLING_ADDRESS("/account/edit-address/billing/");

    public final String url;

    EndPoint(String url){
        this.url = url;
    }
}
