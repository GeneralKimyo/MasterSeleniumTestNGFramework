package org.selenium.pom.factory.abstractfactory;

import org.openqa.selenium.WebDriver;

public abstract class DriverManagerAbstract {

    protected WebDriver driver;

    protected abstract void startDriver();
    public void quitDriver() {
        if (driver != null) {
            quitDriver();
            driver = null;
        }
    }
    public WebDriver getDriver(){
        if(driver ==  null){
            startDriver();
        }
        return driver;
    }
}
