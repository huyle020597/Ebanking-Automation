package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    By loginBoxLocator = By.name("j_idt10:j_idt12");
    By passwordBoxLocator = By.name("j_idt10:j_idt14");
    By loginBtnLocator = By.name("j_idt10:j_idt16");

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public void login (String customerId, String password) {
        driver.findElement(loginBoxLocator).sendKeys(customerId);
        driver.findElement(passwordBoxLocator).sendKeys(password);
        driver.findElement(loginBtnLocator).click();
    }


}
