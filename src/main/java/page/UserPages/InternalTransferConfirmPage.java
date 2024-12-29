package page.UserPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InternalTransferConfirmPage {
    By senderAccount = By.id("j_idt23:j_idt27");
    By availableBalance = By.id("j_idt23:j_idt29");
    By transferAmount = By.id("j_idt23:j_idt31");
    By transferDescription = By.id("j_idt23:j_idt35");
    By receiverAccLocator = By.id("j_idt23:j_idt37");
    By receiverNameLocator = By.id("j_idt23:j_idt39");
    By confirmBtnLocator = By.name("j_idt23:j_idt44");
    By OTPboxLocator = By.name("j_idt23:j_idt46");
    By transferBtnLocator = By.name("j_idt23:j_idt48");

    By transferSuccessMessageLocator = By.xpath("//div[text()='Chuyển tiền thành công']");
    By closeMessageBtnLocator = By.xpath("//*[@class='ui-icon ui-icon-closethick']");

    By wrongOTPMsgLocator = By.xpath("//span[@class='ui-growl-title'][text()='Sai mã OTP']");

    WebDriver driver;

    public InternalTransferConfirmPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Get sender account")
    public String getSenderAcc() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(senderAccount));
        return driver.findElement(senderAccount).getText();
    }

    @Step ("Get available balance")
    public double getAvailableBalance() {
        return Double.parseDouble(driver.findElement(availableBalance).getText()
                .replace(",", "").replace(" VNĐ", ""));
    }

    @Step ("Get transfer amount")
    public double getTransferAmount() {
        return Double.parseDouble(driver.findElement(transferAmount).getText()
                .replace(",", "").replace(" VNĐ", ""));
    }

    @Step ("Get transfer descripton")
    public String getTransferDescription() {
        return driver.findElement(transferDescription).getText();
    }

    @Step ("Get receiver account")
    public String getReceiverAcc() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.stalenessOf(driver.findElement(receiverAccLocator)));
        return driver.findElement(receiverAccLocator).getText();
    }

//    public String getReceiverName() {
//        return driver.findElement(receiverNameLocator).getText();
//    }

    @Step ("Click confirm button")
    public void clickConfirmBtn() {
        driver.findElement(confirmBtnLocator).click();
    }

    @Step ("Input OTP")
    public void inputOTP (String OTPcode) {
        driver.findElement(OTPboxLocator).clear();
        driver.findElement(OTPboxLocator).sendKeys(OTPcode);
    }

    @Step ("Click confirm button")
    public void clickTransferBtn () {
        driver.findElement(transferBtnLocator).click();
    }

    @Step ("Verify if the successful message display")
    public boolean isTransferSuccessMessageDisplayed () {
        WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(transferSuccessMessageLocator));
        return driver.findElement(transferSuccessMessageLocator).isDisplayed();
    }

    @Step ("Close successful message")
    public void closeTransferSuccessMessage () {
        driver.findElement(closeMessageBtnLocator).click();
    }

    @Step ("Verify if the Wrong OTP error message display")
    public boolean isWrongOTPMessageDisplayed () {
        return driver.findElement(wrongOTPMsgLocator).isDisplayed();
    }
}
