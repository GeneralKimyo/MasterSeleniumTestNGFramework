package org.selenium.pom.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.DriverManagerOriginal;
import org.selenium.pom.factory.abstractfactory.DriverManagerAbstract;
import org.selenium.pom.factory.abstractfactory.DriverManagerFactoryAbstract;
import org.selenium.utils.CookieUtils;
import java.util.List;

public class Hooks {

    private final ThreadLocal <WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal <DriverManagerAbstract> driverManager = new ThreadLocal<>();

    private void setDriver(DriverManagerAbstract driverManager){
        this.driverManager.set(driverManager);
    }
    public DriverManagerAbstract getDriverManager() {
        return this.driverManager.get();
    }

    private void setDriver(WebDriver driver){
        this.driver.set(driver);
    }
    public WebDriver getDriver() {
        return this.driver.get();
    }

    @Before
    public void before(Scenario scenario){
        String browser = System.getProperty("browser");
        if (browser==null) browser="CHROME";
        //setDriver2(new DriverManagerOriginal().initializeDriver(browser));
        //setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createrDriver());
        //setDriver(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)).getDriver());
        setDriver(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)));
        setDriver(getDriverManager().getDriver());
        System.out.println("Current Thread: " +Thread.currentThread().getId()+", Driver: " + getDriver());
        //System.out.println("Current Thread: " +Thread.currentThread().threadId()+", Driver: " + getDriverManager());
    }
    @After
    public void after(Scenario scenario) throws Exception {
        Thread.sleep(3000);
        System.out.println("Current Thread: " +Thread.currentThread().getId()+", Driver: " + getDriver());
        String browser = System.getProperty("browser");
        if (browser==null) browser="CHROME";
        getDriver().quit();
    }
    @AfterStep
    public void addScreenshot(Scenario scenario){

        //validate if scenario has failed
        if(scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "image");
        }
    }
    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies =  new CookieUtils().
                convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie: seleniumCookies){
            getDriver().manage().addCookie(cookie);
        }
    }
}
