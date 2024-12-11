package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InternalTransferPage {
    By selectAccDropdownLocator = By.id("j_idt23:j_idt28_label");
    By availableBalanceLocator = By.xpath("//label[@id='j_idt23:amount']");
    By availableAccountLocator = By.xpath("//li[@class='ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all']");

    By receiverAccLocator = By.id("j_idt23:j_idt35");
    By receiverNameLocator = By.xpath("//label[@id='j_idt23:out']");

    By moneyAmountLocator = By.id("j_idt23:j_idt40");
    By transferDescLocator = By.id("j_idt23:j_idt42");

    By confirmBtnLocator = By.name("j_idt23:j_idt44");

    By invalidAccountMsgLocator = By.xpath("//span[@class='ui-growl-title'][text()='Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.']");
    By insufficientMoneyMsgLocator = By.xpath("//span[@class='ui-growl-title'][text()='Số tiền vượt mức']");

    WebDriver driver;

    public InternalTransferPage(WebDriver driver) {
        this.driver = driver;
    }

    private void clickAccDropdown () {driver.findElement(selectAccDropdownLocator).click();}

    public void selectAccountByIndex (int index) {
        List <WebElement> listAvailableAccount = driver.findElements(availableAccountLocator);
        listAvailableAccount.get(index).click();
    }

    public void selectAccountByAccNumber (String AccountNumber) {
        clickAccDropdown();
        By availableAccountLocator = By.xpath(String.format("//li[@class='ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all'][@data-label='%s']",AccountNumber));
        driver.findElement(availableAccountLocator).click();
    }

    public double getAccBalance () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(availableBalanceLocator));
    return Double.parseDouble(driver.findElement(availableBalanceLocator).getText().replace(" VNĐ","").replace(",",""));
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

    public boolean isReceiverNameEmpty () {
        return driver.findElement(receiverNameLocator).getText().equals("");
    }

    public boolean isInvalidAccMessageDisplayed () {
        return driver.findElement(invalidAccountMsgLocator).isDisplayed();
    }


    public boolean isInsufficientMessageDisplayed () {
        return driver.findElement(insufficientMoneyMsgLocator).isDisplayed();
    }
}
