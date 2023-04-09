package org.selenium.pom.base;

import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.DriverManagerFactory;
import org.selenium.pom.factory.abstractfactory.DriverManagerAbstract;
import org.selenium.pom.factory.abstractfactory.DriverManagerFactoryAbstract;
import org.selenium.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {

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
    @Parameters("browser")
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser){
        browser = System.getProperty("browser",browser);
        if (browser== null) browser="CHROME";
        //setDriver(new DriverManagerOriginal().initializeDriver(browser));
        //setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createrDriver());
        //setDriver(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)).getDriver());
        setDriver(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)));
        setDriver(getDriverManager().getDriver());
        System.out.println("Current Thread: " +Thread.currentThread().threadId()+", Driver: " + getDriver());
        //System.out.println("Current Thread: " +Thread.currentThread().threadId()+", Driver: " + getDriverManager());
    }
    @Parameters("browser")
    @AfterMethod
    public synchronized void quitDriver(@Optional String browser , ITestResult result) throws InterruptedException, IOException {
        Thread.sleep(3000);
        System.out.println("Current Thread: " +Thread.currentThread().threadId()+", Driver: " + getDriver());if (browser== null) browser="CHROME";
        if (result.getStatus()== ITestResult.FAILURE){
            File destFile = new File("src"+ File.separator + browser + File.separator+
                    result.getTestClass().getRealClass().getSimpleName()+"_"+
                    result.getMethod().getMethodName()+".png");
            //takeScreenshot(destFile);
            takeScreenshotUsingAshot(destFile);
        }
        getDriver().quit();
    }
    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile,destFile);
    }
    private void takeScreenshotUsingAshot(File destFile){
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
