package vk;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginVk {
    private WebDriver driver;

    @FindBy(id = "index_email")
    private WebElement loginField;

    @FindBy(id = "index_pass")
    private WebElement passwordField;

    @FindBy(id = "index_login_button")
    private WebElement buttonEnter;

    @FindBy(xpath = "//*[@id=\"top_profile_link\"]/div[1]")
    private WebElement accountName;

    public LoginVk(WebDriver driver) {
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
        (new WebDriverWait(driver, 6)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"top_profile_link\"]/div[1]")));
        return accountName.isDisplayed();
    }

}
