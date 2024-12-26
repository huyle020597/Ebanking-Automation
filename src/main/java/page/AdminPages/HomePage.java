package page.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    By customerListLocator = By.xpath("//a[@tabindex='-1']/span[text()='Danh sách khách hàng']");
    By withdrawLocator = By.xpath("//a[@tabindex='-1']/span[text()='Rút tiền']");
    By depositLocator = By.xpath("//a[@tabindex='-1']/span[text()='Nộp Tiền']");
    By customerTransactionListLocator = By.xpath("//a[@tabindex='-1']/span[text()='Theo Khách Hàng']");
    By dayTransactionListLocator = By.xpath("//a[@tabindex='-1']/span[text()='Theo Ngày']");
    By logOutLocator = By.xpath("//input[@name='j_idt9:j_idt19']");

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCustomerListPage() {
        driver.findElement(customerListLocator).click();
    }

    public void openWithdrawPage() {
        driver.findElement(withdrawLocator).click();
    }

    public void openDepositPage() {
        driver.findElement(depositLocator).click();
    }

    public void openCustomerTransactionListPage() {
        driver.findElement(customerTransactionListLocator).click();
    }

    public void openDayTransactionListPage() {
        driver.findElement(dayTransactionListLocator).click();
    }

    public void clickLogOutButton() {
        driver.findElement(logOutLocator).click();
    }


    public void homePage() {
        openCustomerListPage();
        openDepositPage();
        openWithdrawPage();
        openCustomerTransactionListPage();
        openDayTransactionListPage();
        clickLogOutButton();
    }
}
