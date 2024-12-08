package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferConfirmationPage {
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

    public TransferConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getSenderAcc() {
        return driver.findElement(senderAccount).getText();
    }

    public double getAvailableBalance() {
        return Double.parseDouble(driver.findElement(availableBalance).getText()
                .replace(",", "").replace(" VNĐ", ""));
    }

    public double getTransferAmount() {
        return Double.parseDouble(driver.findElement(transferAmount).getText()
                .replace(",", "").replace(" VNĐ", ""));
    }

    public String getTransferDescription() {
        return driver.findElement(transferDescription).getText();
    }

    public String getReceiverAcc() {
        return driver.findElement(receiverAccLocator).getText();
    }

    public String getReceiverName() {
        return driver.findElement(receiverNameLocator).getText();
    }

    public void clickConfirmBtn() {
        driver.findElement(confirmBtnLocator).click();
    }

    public void inputOTP (String OTPcode) {
        driver.findElement(OTPboxLocator).clear();
        driver.findElement(OTPboxLocator).sendKeys(OTPcode);
    }

    public void clickTransferBtn () {
        driver.findElement(transferBtnLocator).click();
    }

    public boolean isTransferSuccessMessageDisplayed () {
        return driver.findElement(transferSuccessMessageLocator).isDisplayed();
    }

    public void closeTransferSuccessMessage () {
        driver.findElement(closeMessageBtnLocator).click();
    }

    public boolean isWrongOTPMessageDisplayed () {
        return driver.findElement(wrongOTPMsgLocator).isDisplayed();
    }
}
