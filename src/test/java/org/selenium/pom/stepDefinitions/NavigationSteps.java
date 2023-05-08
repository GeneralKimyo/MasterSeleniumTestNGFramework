package org.selenium.pom.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.pages.Homepage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;



public class NavigationSteps{

    WebDriver driver;
    public NavigationSteps(Hooks baseTest){
        this.driver = baseTest.getDriver();
    }
    @Given("I am on the home page")
    public void i_am_on_the_home_page(){
        System.out.println("im on step 1");
        Homepage homepage = new Homepage(driver).load();
    }

    @When("I click on store menu link")
    public void i_click_on_store_menu_link(){
        StorePage storePage = new Homepage(driver).
                getMyHeader().
                navigateToStoreUsingMenu();
    }
    @Then("I should see the store page")
    public void i_should_see_the_store_page(){
        String expectedUrl = "https://askomdch.com/store/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl,expectedUrl);
    }
}
