package page.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DepositPage {
    By receiveAccountTextbox = By.name("j_idt23:j_idt27");
    By amountTextbox = By.name("j_idt23:j_idt29");
    By paymentNoteTextbox = By.name("j_idt23:j_idt31");
    By confirmBtnLocator = By.name("j_idt23:j_idt33");
    By successMessageLocator = By.xpath("(//label[contains(text(),'nộp tiền thành công')])[1]");

    WebDriver driver;

    public DepositPage (WebDriver driver) {
        this.driver = driver;
    }

    public void inputReceiveAccount(String accountNumber) {
        driver.findElement(receiveAccountTextbox).sendKeys(accountNumber);
    }

    public void inputAmount(Double amount) {
        driver.findElement(amountTextbox).sendKeys(Double.toString(amount));
    }

    public void inputNote(String paymentNote) {
        driver.findElement(paymentNoteTextbox).sendKeys(paymentNote);
    }

    public void clickConfirm() {
        driver.findElement(confirmBtnLocator).click();
    }



    public void inputDeposit(String accountNumber, double amount, String paymentNote) {
        inputReceiveAccount(accountNumber);
        inputAmount(amount);
        inputNote(paymentNote);
        clickConfirm();
    }

    public boolean isSuccessfulMsgDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
        return driver.findElement(successMessageLocator).isDisplayed();
    }
}
