package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage {
    By oldPWlocator = By.id("j_idt24:j_idt28");
    By newPWlocator = By.xpath("//input[@id='j_idt24:pwd1']");
    By confirmPWlocator = By.xpath("//input[@id='j_idt24:pwd2']");
    By changePWbuttonLocator = By.name("j_idt24:j_idt32");

    WebDriver driver;

    public ChangePasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputOldPW (String oldPW) {
        driver.findElement(oldPWlocator).sendKeys(oldPW);
    }
    public void inputNewPW (String newPW) {
        driver.findElement(newPWlocator).sendKeys(newPW);
    }
    public void inputConfirmPW (String confirmPW) {
        driver.findElement(confirmPWlocator).sendKeys(confirmPW);
    }
    public void clickChangePWbutton () {
        driver.findElement(confirmPWlocator).click();
    }

    public void changePassword(String oldPW, String newPW) {
        inputOldPW(oldPW);
        inputNewPW(newPW);
        inputConfirmPW(newPW);
        clickChangePWbutton();
    }
}
