package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExternalTransferPage {
    By accountDropdownLocator = By.id("j_idt23:j_idt28_label");
    By availableAccountsLocator;
    By senderBalanceLocator = By.xpath("//label[@id='j_idt23:amount']");
    By receiverAccountBoxLocator = By.xpath("//input[@id='j_idt23:soucre']");
    By receiverNameLocator = By.xpath("//input[@id='j_idt23:nameSoucre']");

    By banksDropdownLocator = By.id("j_idt23:country_label");
    By branchDropdownLocator = By.id("j_idt23:city_label");
    By availableBanksLocator;
    By availableBranchsLocator;

    By transferDescLocator = By.id("j_idt23:j_idt45");
    By transferAmountLocator = By.xpath("//input[@id='j_idt23:tranf']");

    By transferBtnLocator = By.name("j_idt23:j_idt48");

    WebDriver driver;

    public ExternalTransferPage(WebDriver driver) {
        this.driver = driver;
    }

    private void clickAccountDropdown() {
        driver.findElement(accountDropdownLocator).click();
    }

    public void selectAccountByAccNumber(String accNumber) {
        clickAccountDropdown();
        availableAccountsLocator = By.xpath(String.format("//li[@class='ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all'][@data-label='%s']",accNumber));
        driver.findElement(availableAccountsLocator).click();
    }

    public double getSenderBalance() {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(senderBalanceLocator)));
        return Double.parseDouble(driver.findElement(senderBalanceLocator).getText().replace(" VNĐ","").replace(",",""));
    }

    public void inputReceiverAccount(String receiverAccount) {
        driver.findElement(receiverAccountBoxLocator).clear();
        driver.findElement(receiverAccountBoxLocator).sendKeys(receiverAccount);
    }

    public void inputReceiverName (String receiverName) {
       driver.findElement(receiverNameLocator).sendKeys(receiverName);
    }

    public void clickBanksDropdown() {
        driver.findElement(banksDropdownLocator).click();
    }
    private void selectAvailableBanks(String bankName) {
        clickBanksDropdown();
        availableBanksLocator = By.xpath(String.format("//li[@class='ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all'][@data-label='%s']",bankName));
        driver.findElement(availableBanksLocator).click();
    }
    public void selectDongABank() {
        selectAvailableBanks("Ngân hàng Đông Á");
    }


    public void clickBranchsDropdown() {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(branchDropdownLocator)));
        driver.findElement(branchDropdownLocator).click();
    }
    private void selectAvailableBranch(String branchName) {
        clickBranchsDropdown();
        availableBranchsLocator = By.xpath(String.format("//li[@class='ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all'][@data-label='%s']",branchName));
        driver.findElement(availableBranchsLocator).click();
    }
    public void selectDaNangBranch() {
        selectAvailableBranch("Chi nhánh Đà Nẵng");
    }

    public void inputTransferDescription (String transferDesc) {
        driver.findElement(transferDescLocator).sendKeys(transferDesc);
    }

    public void inputTransferAmount (double transferAmount) {
        driver.findElement(transferAmountLocator).sendKeys(Double.toString(transferAmount));
    }

    public void clickTransferBtn () {
        driver.findElement(transferBtnLocator).click();
    }


}
