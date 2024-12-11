package page.UserPages;

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

    public String getSenderAcc () {return driver.findElement(senderAccountLocator).getText();}

    public double getSenderBalance () {
        return Double.parseDouble(driver.findElement(senderBalanceLocator)
                .getText().replace(",","").replace(" VNĐ",""));
    }

    public String getReceiverAcc () {return driver.findElement(receiverAccountLocator).getText();}

    public double getTransferAmount () {
        return Double.parseDouble(driver.findElement(transferAmountLocator).getText()
                .replace(",","").replace(" VNĐ",""));
    }

    public String getTransferDesc() {return driver.findElement(transferDescLocator).getText();}

    public String getReceiverName() {
        return driver.findElement(receiverNameLocator).getText();
    }

    public void clickConfirmBtn() {
        driver.findElement(confirmBtnLocator).click();
    }

    public void inputOTP (String OTP) {
        driver.findElement(OTPcodeBoxLocator).clear();
        driver.findElement(OTPcodeBoxLocator).sendKeys(OTP);
    }

    public void clickTransferBtn() {
        driver.findElement(transferBtnLocator).click();
    }

    public boolean isSuccessMessageDisplayed () {
        return driver.findElement(transferSuccessMessageLocator).isDisplayed();
    }

    public void closeSuccessMessage () {
        driver.findElement(closeMessageBtnLocator).click();
    }
}
