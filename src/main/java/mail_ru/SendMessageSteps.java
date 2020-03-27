package mail_ru;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SendMessageSteps {

    private static final String APIKEY = "4Rw7OPeDmSKTfCufkvhS1l4Vi4lyzvj0NtnGA1vo5Pw110";
    private static final int TIMEOUT = 4;
    private WebDriver driver;
    private SendMessage sendingMessagePage;
    private TestResultsSummary allTestResults;

    @Given("^I am on main page mail.ru$")
    public void getMainPage() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://mail.ru");
        driver.manage().window().maximize();
        allTestResults = takeScreenShot("mainPage window", "mainPageStep");
        Assert.assertTrue(allTestResults.getAllResults()[0].getTestResults().isPassed());
    }

    @And("^I am signed in to account with login \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void loginToAccount(String account, String password) {
        LoginMailRu loginPage = new LoginMailRu(driver);
        loginPage.enterLogin(account);
        loginPage.enterPassword(password);
        (new WebDriverWait(driver, 6)).until(ExpectedConditions.elementToBeClickable(By.xpath(".//i[@id='PH_user-email']")));
        allTestResults = takeScreenShot("InboxPage window", "loginStep");
        Assert.assertTrue(allTestResults.getAllResults()[0].getTestResults().isPassed());
    }

    @When("^I click the button create new message$")
    public void createNewMessage() {
        sendingMessagePage = new SendMessage(driver);
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        sendingMessagePage.createNewLetter();
        WebElement maximizeButton = driver.findElement(By.xpath("(//div[@class='container--1HmQy'])[4]"));
        maximizeButton.click();
        allTestResults = takeScreenShot("New message window", "NewMessageStep");
        Assert.assertTrue(allTestResults.getAllResults()[0].getTestResults().isPassed());
    }

    @And("^Send it to \"([^\"]*)\" with subject \"([^\"]*)\" and text \"([^\"]*)\"$")
    public void sendMessage(String emails, String subject, String text) {
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        sendingMessagePage.fillMessage(emails, subject, text);
        allTestResults = takeScreenShot("Ready message window", "ReadyMessageStep");
        Assert.assertTrue(allTestResults.getAllResults()[0].getTestResults().isPassed());
        sendingMessagePage.clickSendButton();
    }

    @Then("^Confirmation phrase \"([^\"]*)\" is displayed$")
    public void checkConfirmation(String confirmPhrase) {
        Assert.assertTrue(confirmPhrase.equalsIgnoreCase(sendingMessagePage.seeConfirmation()));
        allTestResults = takeScreenShot("Confirmation window", "ConfirmationStep");
        Assert.assertTrue(allTestResults.getAllResults()[0].getTestResults().isPassed());
    }

    public TestResultsSummary takeScreenShot(String windowName, String stepName) {
        EyesRunner runner = new ClassicRunner();
        Eyes eyes = new Eyes(runner);
        eyes.setMatchLevel(MatchLevel.LAYOUT);
        eyes.setApiKey(APIKEY);
        eyes.open(driver, "SendingMessage page", stepName);
        eyes.checkWindow(windowName);
        eyes.closeAsync();
        eyes.abortIfNotClosed();
        TestResultsSummary allTestResults = runner.getAllTestResults();
        return allTestResults;
    }
}
