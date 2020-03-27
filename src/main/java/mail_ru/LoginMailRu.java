package mail_ru;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginMailRu {

    private WebDriver driver;

    @FindBy(id = "mailbox:login")
    private WebElement loginField;

    @FindBy(id = "mailbox:password")
    private WebElement passwordField;

    @FindBy(xpath = ".//*[@id='mailbox:submit']/input")
    private WebElement buttonEnter;

    @FindBy(xpath = ".//i[@id='PH_user-email']")
    private WebElement accountName;

    public LoginMailRu(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void enterLogin(String login) {
        loginField.clear();
        loginField.sendKeys(login);
        buttonEnter.click();
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
        buttonEnter.click();
    }

    public boolean logoutLinkPresents() {
        (new WebDriverWait(driver, 6)).until(ExpectedConditions.elementToBeClickable(By.xpath(".//i[@id='PH_user-email']")));
        return accountName.isDisplayed();
    }
}
