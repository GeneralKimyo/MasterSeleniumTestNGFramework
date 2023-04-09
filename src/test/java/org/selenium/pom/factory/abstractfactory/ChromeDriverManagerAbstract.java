package org.selenium.pom.factory.abstractfactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManagerAbstract extends DriverManagerAbstract {


    @Override
    protected void startDriver() {
        WebDriverManager.chromedriver().cachePath("Drivers").setup();
        //bypass
        ChromeOptions options =  new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
}
