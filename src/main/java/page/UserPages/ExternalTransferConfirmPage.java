package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExternalTransferConfirmPage {
    By receiverNameLocator = By.xpath("//label[text()=' Tên chủ tài khoản']/../following-sibling::td/label");
    By confirmBtnLocator = By.name("j_idt23:j_idt44");
    By OTPcodeBoxLocator = By.name("j_idt23:j_idt45");
    By transferBtnLocator = By.name("j_idt23:j_idt47");

    By transferSuccessMessageLocator = By.xpath("//div[text()='Chuyển tiền thành công']");
    WebDriver driver;

    public ExternalTransferConfirmPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getReceiverName() {
       return driver.findElement(receiverNameLocator).getText();
    }

    public void clickConfirmBtn() {
        driver.findElement(confirmBtnLocator).click();
    }

    public void inputOTPcode (String OTP) {
        driver.findElement(OTPcodeBoxLocator).clear();
        driver.findElement(OTPcodeBoxLocator).sendKeys(OTP);
    }

    public void clickTransferBtn() {
        driver.findElement(transferBtnLocator).click();
    }

    public boolean isSuccessMessageDisplayed () {
        return driver.findElement(transferSuccessMessageLocator).isDisplayed();
    }
}
