package org.selenium.pom.api.actions;

import org.selenium.pom.objects.User;
import org.selenium.utils.FakerUtils;

public class DummyTestClass {
    public static void main(String[] Args){
        String username = "demouser" +new FakerUtils().generateRandomNumber();
        User user= new User();
        user.setUserName(username);
        user.setPassword("demopw");
        user.setEmail(username + "@askomdch.com");
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        System.out.println("Register cookies: "+signUpApi.getCookies());
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(1215,1);
        System.out.println("Cart cookies: "+cartApi.getCookies());

    }
}
