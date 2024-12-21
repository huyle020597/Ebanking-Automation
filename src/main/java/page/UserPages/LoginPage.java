package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    By loginBoxLocator = By.name("j_idt10:j_idt12");
    By passwordBoxLocator = By.name("j_idt10:j_idt14");
    By loginBtnLocator = By.name("j_idt10:j_idt16");
    By registerBtnLocator = By.linkText("Tạo tài khoản");
    By forgotPasswordLocator = By.linkText("Quên mật khẩu");

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickRegisterBtn() {
        driver.findElement(registerBtnLocator).click();
    }

    public void clickForgotPass() {
        driver.findElement(forgotPasswordLocator).click();
    }


    public void login (String customerId, String password) {
        driver.findElement(loginBoxLocator).sendKeys(customerId);
        driver.findElement(passwordBoxLocator).sendKeys(password);
        driver.findElement(loginBtnLocator).click();
    }


}
