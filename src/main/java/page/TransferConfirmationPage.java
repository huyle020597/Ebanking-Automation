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

}
