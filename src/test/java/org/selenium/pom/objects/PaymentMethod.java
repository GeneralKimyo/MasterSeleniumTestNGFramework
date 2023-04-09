package org.selenium.pom.objects;

import org.selenium.utils.JacksonUtils;

import java.io.IOException;

public class PaymentMethod {

    public PaymentMethod(){}

    private String paymentMethod;

    public int getId() {
        return id;
    }

    private int id;
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public  PaymentMethod(int id) throws IOException {
    PaymentMethod[] pms = JacksonUtils.deserializeJson("paymentMethod.json",PaymentMethod[].class);
    for(PaymentMethod pm: pms)
    {
        if(pm.getId() == id){
            this.id= id;
            this.paymentMethod = getPaymentMethod();
        }
    }
    }
}
