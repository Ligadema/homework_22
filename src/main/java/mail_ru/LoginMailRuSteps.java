package mail_ru;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import vk.LoginVk;

public class LoginMailRuSteps {

    private WebDriver driver;
    private LoginVk loginPage;

    @Given("^I am on a main application page$")
    public void getMainPage() {
        driver = new ChromeDriver();
        driver.get("http://mail.ru");
        driver.manage().window().maximize();
    }

    @When("^I login as a correct user$")
    public void login() {
        loginPage = new LoginVk(driver);
        loginPage.enterLogin("sid-vlada@mail.ru");
        loginPage.enterPassword("18091991mail");
    }

    @Then("^user name is visible$")
    public void seeLogoutLink() {
        Assert.assertTrue(loginPage.logoutLinkPresents());
    }
}
