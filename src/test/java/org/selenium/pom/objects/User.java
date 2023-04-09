package org.selenium.pom.objects;
import org.selenium.utils.JacksonUtils;

import java.io.IOException;

public class User {


    private String userName;
    private String email;
    private String password;

    public User(){

    }
    public User(String userName, String email, String password){
        this.userName = userName;
        this.email= email;
        this.password= password;
    }
    public User(String userName, String password){
        this.userName = userName;
        this.password= password;
    }
    public User(String userName) throws IOException {
    User[] userPass = JacksonUtils.deserializeJson("myLogIn.json", User[].class);
    for(User user:userPass){
        if(user.getUserName().equals(userName)){
            this.userName =  userName;
            this.password = user.getPassword();
        }
        }
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword() {
        return password;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

}
