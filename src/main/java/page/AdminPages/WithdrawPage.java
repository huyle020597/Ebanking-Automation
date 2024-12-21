package page.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WithdrawPage {
    By accountNumberTextbox = By.name("j_idt23:j_idt27");
    By AmountTextbox = By.name("j_idt23:j_idt29");
    By noteTextbox = By.name("j_idt23:j_idt31");
    By transferBtn = By.name("j_idt23:j_idt34");
    By successMessageLocator = By.xpath("//label[contains(text(),'nh công')]");

    WebDriver driver;

    public WithdrawPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputAccountNumber(String account) {
        driver.findElement(accountNumberTextbox).sendKeys(account);
    }

    public void inputAmount(double amount) {
        driver.findElement(AmountTextbox).sendKeys(Double.toString(amount));
    }

    public void inputNote(String note) {
        driver.findElement(noteTextbox).sendKeys(note);
    }

    public void clickTransfer() {
        driver.findElement(transferBtn).click();
    }


    public void withdraw(String account, double amount, String note) {
        inputAccountNumber(account);
        inputAmount(amount);
        inputNote(note);
        clickTransfer();
    }

    public boolean isSuccessfulMsgDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
        return driver.findElement(successMessageLocator).isDisplayed();
    }
}
