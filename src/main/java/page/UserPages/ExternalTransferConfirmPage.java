package page.UserPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExternalTransferConfirmPage {
    By senderAccountLocator = By.xpath("//label[text()='Tài khoản gửi']/../following-sibling::td/label");
    By senderBalanceLocator = By.xpath("//label[text()=' Số tiền trong tài khoản']/../following-sibling::td/label");
    By receiverAccountLocator = By.xpath("//label[text()='Tài khoản nhận']/../following-sibling::td/label");
    By transferAmountLocator = By.xpath("//label[text()='Số tiền chuyển khoản']/../following-sibling::td/label");
    By transferDescLocator = By.xpath("//label[text()='Nội dung chuyển khoản']/../following-sibling::td/label");
    By receiverNameLocator = By.xpath("//label[text()=' Tên chủ tài khoản']/../following-sibling::td/label");

    By confirmBtnLocator = By.name("j_idt23:j_idt44");
    By OTPcodeBoxLocator = By.name("j_idt23:j_idt45");
    By transferBtnLocator = By.name("j_idt23:j_idt47");


    By transferSuccessMessageLocator = By.xpath("//div[text()='Chuyển tiền thành công']");
    By closeMessageBtnLocator = By.xpath("//span[@class='ui-icon ui-icon-closethick']");
    WebDriver driver;

    public ExternalTransferConfirmPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step ("Get sender account")
    public String getSenderAcc () {return driver.findElement(senderAccountLocator).getText();}

    @Step ("Get sender balance")
    public double getSenderBalance () {
        return Double.parseDouble(driver.findElement(senderBalanceLocator)
                .getText().replace(",","").replace(" VNĐ",""));
    }

    @Step ("Get receiver account")
    public String getReceiverAcc () {return driver.findElement(receiverAccountLocator).getText();}

    @Step ("Get transaction ammount")
    public double getTransferAmount () {
        return Double.parseDouble(driver.findElement(transferAmountLocator).getText()
                .replace(",","").replace(" VNĐ",""));
    }

    @Step ("Get transfer description")
    public String getTransferDesc() {return driver.findElement(transferDescLocator).getText();}

    @Step ("Get receiver name")
    public String getReceiverName() {
        return driver.findElement(receiverNameLocator).getText();
    }

    @Step ("Click confirm button")
    public void clickConfirmBtn() {
        driver.findElement(confirmBtnLocator).click();
    }

    @Step ("Input OTP")
    public void inputOTP (String OTP) {
        driver.findElement(OTPcodeBoxLocator).clear();
        driver.findElement(OTPcodeBoxLocator).sendKeys(OTP);
    }

    @Step ("Click transfer button")
    public void clickTransferBtn() {
        driver.findElement(transferBtnLocator).click();
    }

    @Step ("Verify that the successful message display")
    public boolean isSuccessMessageDisplayed () {
        return driver.findElement(transferSuccessMessageLocator).isDisplayed();
    }

    @Step ("Close successful message")
    public void closeSuccessMessage () {
        driver.findElement(closeMessageBtnLocator).click();
    }
}
