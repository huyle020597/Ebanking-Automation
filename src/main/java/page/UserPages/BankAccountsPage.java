package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BankAccountsPage extends MenuBar{
    By accountNumberLocator;
    By accountBalanceLocator = By.xpath("//label[text()='Số dư tài khoản']/../following-sibling::td/label");


    public BankAccountsPage(WebDriver driver) {
        super(driver);
    }

    public void viewDetailsByAccNumber (String accountNumber) {
        accountNumberLocator = By.xpath(String.format("//td[@role='gridcell']/a[text()='%s']",accountNumber));
        driver.findElement(accountNumberLocator).click();
    }

    public void viewDetailsByIndex (int index) {
        List < WebElement> listBankAccNumbers = driver.findElements(By.xpath("//td[@role='gridcell']/a"));
        listBankAccNumbers.get(index-1).click();
    }

    public double getAccountBalance () {
        return Double.parseDouble(driver.findElement(accountBalanceLocator).getText().replace(" VNĐ","").replace(",",""));
    }

    public int getQuantityAccount () {
        List < WebElement> listBankAccNumbers = driver.findElements(By.xpath("//td[@role='gridcell']/a"));
        return listBankAccNumbers.size();
    }

}
