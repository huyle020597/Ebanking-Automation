package example.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    By customerListLocator = By.xpath("(//span[text()='Danh sách khách hàng'])[1]");
    By withDrawLocator = By.xpath("(//span[text()='Rút tiền'])");
    By depositLocator = By.xpath("//span[contains(text(),'Nộp Tiền')]");
    By customerTransactionListLocator = By.xpath("(//span[text()='Theo Khách Hàng'])");
    By dayTransactionListLocator = By.xpath("(//span[normalize-space()='Theo Ngày'])");
    By logOutLocator = By.xpath("//input[@name='j_idt9:j_idt19']");

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCustomerList() {
        driver.findElement(customerListLocator).click();
    }

    public void openWithdrawButton() {
        driver.findElement(withDrawLocator).click();
    }

    public void openDepositPage() {
        driver.findElement(depositLocator).click();
    }

    public void openCustomerTransactionList() {
        driver.findElement(customerTransactionListLocator).click();
    }

    public void openDayTransactionList() {
        driver.findElement(dayTransactionListLocator).click();
    }

    public void clickLogOutButton() {
        driver.findElement(logOutLocator).click();
    }

    public void homePage() {
    }
}
