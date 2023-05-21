package org.selenium.pom.base;

import io.qameta.allure.Attachment;
import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.DriverManagerFactory;
import org.selenium.pom.factory.DriverManagerOriginal;
import org.selenium.pom.factory.abstractfactory.DriverManagerAbstract;
import org.selenium.pom.factory.abstractfactory.DriverManagerFactoryAbstract;
import org.selenium.pom.management.tool.TestRailClient;
import org.selenium.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class BaseTest {

    private final ThreadLocal <WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal <DriverManagerAbstract> driverManager = new ThreadLocal<>();

    public WebDriver getDriver2() {
        return driver2;
    }

    public void setDriver2(WebDriver driver2) {
        this.driver2 = driver2;
    }

    private  WebDriver driver2;


    private TestRailClient testRailClient = new TestRailClient();
    public TestRailClient getTestRailClient() {
        return testRailClient;
    }

    public void setTestRailClient(TestRailClient testRailClient) {
        this.testRailClient = testRailClient;
    }


    private void setDriverManager(DriverManagerAbstract driverManager){
        this.driverManager.set(driverManager);
    }
    public DriverManagerAbstract getDriverManager() {
        return this.driverManager.get();
    }

    private String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private void setDriver(WebDriver driver){
        this.driver.set(driver);
    }
    public WebDriver getDriver() {
        return this.driver.get();
    }
    @Parameters("browser")
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser){
        browser = System.getProperty("browser");
        if (browser==null) browser="FIREFOX";
        //setDriver2(new DriverManagerOriginal().initializeDriver(browser));
        //setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createrDriver());
        //setDriver(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)).getDriver());
        setDriverManager(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)));
        setDriver(getDriverManager().getDriver());
        System.out.println("Current Thread: " +Thread.currentThread().getId()+", Driver: " + getDriver());
        //System.out.println("Current Thread: " +Thread.currentThread().threadId()+", Driver: " + getDriverManager());
    }

    @Parameters("browser")
    @AfterMethod
    public synchronized void quitDriver(@Optional String browser, ITestResult result, Method method) throws Exception {
        Thread.sleep(3000);
        System.out.println("Current Thread: " +Thread.currentThread().getId()+", Driver: " + getDriver());
        //browser = System.getProperty("browser");
        if (browser==null) browser="FIREFOX";
        if (result.getStatus()== ITestResult.SUCCESS){setStatus("pass");}
        if (result.getStatus()== ITestResult.FAILURE){
        File destFile = new File("src"+ File.separator + browser + File.separator+
        result.getTestClass().getRealClass().getSimpleName()+"_"+
        result.getMethod().getMethodName()+".png");
        //takeScreenshot(destFile);
        takeScreenshotUsingAshot(destFile);
         setStatus("fail");
        }
        getDriver().quit();
        //testRailClient.beforeTestResult(method);
        //testRailClient.addTestResult(result.getMethod().getMethodName(),getStatus());
    }
    public void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile,destFile);
    }
    @Attachment
    public void takeScreenshotUsingAshot(File destFile){
        Screenshot screenshot = new AShot().
                shootingStrategy(ShootingStrategies.viewportPasting(100)).
                takeScreenshot(getDriver());
        try{
            ImageIO.write(screenshot.getImage(),"PNG",destFile);
        }
        catch(IOException e){
            e.printStackTrace();
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

