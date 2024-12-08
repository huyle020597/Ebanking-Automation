package example.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    By idInputLocator = By.xpath("//input[@id='j_idt9:id1']");
    By passwordInputLocator = By.xpath("//input[@id='j_idt9:pwd1']");
    By loginButtonLocator = By.xpath("//span[text()='Login']");

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterID(String id) {
        driver.findElement(idInputLocator).sendKeys(id);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInputLocator).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButtonLocator).click();
    }

    public void loginAdmin(String id, String password) {
        enterID(id);
        enterPassword(password);
        clickLogin();
    }
}
