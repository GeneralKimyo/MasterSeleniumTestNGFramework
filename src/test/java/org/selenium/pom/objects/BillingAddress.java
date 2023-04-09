package org.selenium.pom.objects;

import org.selenium.utils.JacksonUtils;

import java.io.IOException;

public class BillingAddress {

    private int billingId;
    private boolean usRegion;
    private String firstName;
    private String lastName;
    private String country;
    private String state;
    private String addressLineOne;
    private String city;
    private String postalCode;
    private String email;
    private String company;
    private String phone;

    public BillingAddress(){

    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public BillingAddress setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BillingAddress setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
    public String getAddressLineOne() {
        return addressLineOne;
    }

    public BillingAddress setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
        return this;
    }

    public String getCity() {
        return city;
    }

    public BillingAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public BillingAddress setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BillingAddress setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    public boolean isUsRegion() {
        return usRegion;
    }

    public void setUsRegion(boolean usRegion) {
        this.usRegion = usRegion;
    }

    public BillingAddress(int billingId) throws IOException {
        BillingAddress[] billingAddresses = JacksonUtils.
                deserializeJson("myBillingAddress.json",BillingAddress[].class);
        for(BillingAddress billingAddress:billingAddresses) {
            if(billingAddress.getBillingId()==billingId){
                this.billingId = billingAddress.getBillingId();
                this.firstName = billingAddress.getFirstName();
                this.lastName =  billingAddress.getLastName();
                this.company =  billingAddress.getCompany();
                this.country =  billingAddress.getCountry();
                this.state =  billingAddress.getState();
                this.addressLineOne =  billingAddress.getAddressLineOne();
                this.city =  billingAddress.getCity();
                this.postalCode = billingAddress.getPostalCode();
                this.phone= billingAddress.getPhone();
                this.email =  billingAddress.getEmail();
                this.usRegion = billingAddress.isUsRegion();
            }
        }
    }

}
