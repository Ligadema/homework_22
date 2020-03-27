package vk;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginVkSteps {

    private WebDriver driver;
    private LoginVk loginVkPage;

    @Given("^I went to login page$")
    public void getMainPage() {
        driver = new ChromeDriver();
        driver.get("http://vk.com");
        driver.manage().window().maximize();
    }

    @When("^I login as correct user$")
    public void login() {
        loginVkPage = new LoginVk(driver);
        loginVkPage.enterLogin("sid-vlada@yandex.ru");
        loginVkPage.enterPassword("12345");
    }

    @Then("^user name is visible$")
    public void seeLogoutLink() {
        Assert.assertTrue(loginVkPage.logoutLinkPresents());
    }
}

