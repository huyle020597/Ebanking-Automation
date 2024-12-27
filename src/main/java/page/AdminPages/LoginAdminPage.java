package page.AdminPages;

import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginAdminPage {
    By idInputLocator = By.xpath("//input[@id='j_idt9:id1']");
    By passwordInputLocator = By.xpath("//input[@id='j_idt9:pwd1']");
    By loginButtonLocator = By.xpath("//span[text()='Login']");

    WebDriver driver;

    public LoginAdminPage(WebDriver driver) {
        this.driver = driver;
    }


    public void loginAdmin (User admin) {
        driver.findElement(idInputLocator).sendKeys(admin.getUserId());
        driver.findElement(passwordInputLocator).sendKeys(admin.getPassword());
        driver.findElement(loginButtonLocator).click();
    }

}
