package page.UserPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BankAccountsPage extends MenuBar{
    // Accounts list
    By accountNumberLocator;
    By accountBalanceLocator = By.xpath("//label[text()='Số dư tài khoản']/../following-sibling::td/label");

    //Latest Transactions list
    By transactionDate;
    By transactionAccNumber;
    By transactionAmount;


    public BankAccountsPage(WebDriver driver) {
        super(driver);
    }

    @Step ("Open an account details by account number")
    public void viewDetailsByAccNumber (String accountNumber) {
        accountNumberLocator = By.xpath(String.format("//td[@role='gridcell']/a[text()='%s']",accountNumber));
        driver.findElement(accountNumberLocator).click();
    }

    @Step ("Open an account details by index")
    public void viewDetailsByIndex (int index) {
        List < WebElement> listBankAccNumbers = driver.findElements(By.xpath("//td[@role='gridcell']/a"));
        listBankAccNumbers.get(index-1).click();
    }

    @Step ("Get account number by index")
    public String getAccountNoByIndex(int index) {
        List < WebElement> listBankAccNumbers = driver.findElements(By.xpath("//td[@role='gridcell']/a"));
        return  listBankAccNumbers.get(index-1).getText();
    }

    @Step ("Get account balance")
    public double getAccountBalance () {
        return Double.parseDouble(driver.findElement(accountBalanceLocator).getText().replace(" VNĐ","").replace(",",""));
    }

    @Step ("Count quantity of existing accounts")
    public int getQuantityAccount () {
        List < WebElement> listBankAccNumbers = driver.findElements(By.xpath("//td[@role='gridcell']/a"));
        return listBankAccNumbers.size();
    }

    @Step ("Get transaction date by row number")
    public String getTransactionDateByIndex(int index) {
        transactionDate = By.xpath(String.format("(//tbody[@id='j_idt37_data']/tr/td)[%s]",(index-1)*3+1));
        return driver.findElement(transactionDate).getText();
    }

    @Step ("Get transaction account number by row")
    public String getTransactionAccNumberByIndex (int index) {
        transactionAccNumber = By.xpath(String.format("(//tbody[@id='j_idt37_data']/tr/td)[%s]",(index-1)*3+2));
        return driver.findElement(transactionAccNumber).getText();
    }

    @Step ("Get actual transaction amount by row")
    public double getActualTransactionAmountByIndex (int index) {
        transactionAmount = By.xpath(String.format("(//tbody[@id='j_idt37_data']/tr/td)[%s]",(index-1)*3+3));
        return Double.parseDouble(driver.findElement(transactionAmount).getText().substring(2).
                replace(",","").replace(" VNĐ",""));
    }


    public double getTransactionAmountByIndex (int transactionIndex) {
        transactionAmount = By.xpath(String.format("(//tbody[@id='j_idt37_data']/tr/td)[%s]", (transactionIndex - 1) * 3 + 3));
        return Double.parseDouble(driver.findElement(transactionAmount).getText()
                .replace(" ", "").replace(",", "").replace("VNĐ", ""));
    }
}
