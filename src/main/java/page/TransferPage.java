package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TransferPage {
    By selectAccDropdownLocator = By.id("j_idt23:j_idt28_label");
    By availableBalanceLocator = By.xpath("//label[@id='j_idt23:amount']");
    By availableAccountLocator = By.xpath("//li[@class='ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all']");

    By receiverAccLocator = By.id("j_idt23:j_idt35");
    By receiverNameLocator = By.xpath("");

    By moneyAmountLocator = By.id("j_idt23:j_idt40");
    By transferDescLocator = By.id("j_idt23:j_idt42");

    By confirmBtnLocator = By.name("j_idt23:j_idt44");

    WebDriver driver;

    public TransferPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAccDropdown () {driver.findElement(selectAccDropdownLocator).click();}

    public void selectAccountByIndex (int index) {
        List <WebElement> listAvailableAccount = driver.findElements(availableAccountLocator);
        listAvailableAccount.get(index).click();
    }


    public double getAccBalance () {
    return Double.parseDouble(driver.findElement(availableBalanceLocator).getText().replace(" VNĐ",""));
    }

    public void inputReceiverAccount (String receiverAccount) {
        driver.findElement(receiverAccLocator).clear();
        driver.findElement(receiverAccLocator).sendKeys(receiverAccount);
    }

    public String getReceiverName () {
    return driver.findElement(receiverNameLocator).getText();
    }

    public void inputMoneyAmount (double moneyAmount) {
        driver.findElement(moneyAmountLocator).clear();
        driver.findElement(moneyAmountLocator).sendKeys(Double.toString(moneyAmount));
    }

    public void inputTransferDescription (String description) {
        driver.findElement(transferDescLocator).clear();
        driver.findElement(transferDescLocator).sendKeys(description);
    }

    public void clickConfirmBtn () {driver.findElement(confirmBtnLocator).click();}

}
