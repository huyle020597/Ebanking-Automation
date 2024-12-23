package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChangePasswordPage extends MenuBar {
    By oldPWlocator = By.id("j_idt24:j_idt28");
    By newPWlocator = By.xpath("//input[@id='j_idt24:pwd1']");
    By confirmPWlocator = By.xpath("//input[@id='j_idt24:pwd2']");
    By changePWbuttonLocator = By.name("j_idt24:j_idt32");

    By successMessageLocator = By.xpath("//div[@class='ui-dialog-content ui-widget-content'][text()='Đổi mật khẩu thành công']");
    By closeSuccessMessageLocator = By.xpath("//span[@class='ui-icon ui-icon-closethick']");

    WebDriver driver;

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
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
        driver.findElement(changePWbuttonLocator).click();
    }

    public void changePassword(String oldPW, String newPW) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(oldPWlocator));
        inputOldPW(oldPW);
        inputNewPW(newPW);
        inputConfirmPW(newPW);
        clickChangePWbutton();
    }

    public boolean isSuccessMessageDisplayed () {
     return    driver.findElement(successMessageLocator).isDisplayed();
    }
    public void closeSuccessMessage () {
        driver.findElement(closeSuccessMessageLocator).click();
    }

}
