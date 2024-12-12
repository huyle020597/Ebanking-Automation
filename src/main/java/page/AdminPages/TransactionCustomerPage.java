package page.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TransactionCustomerPage {
    By accountNumberTextbox = By.xpath("(//input[@id='j_idt23:mas'])[1]");
    By dateFromTextbox = By.id("j_idt23:mask_input");
    By dateToTextbox = By.id("j_idt23:masks_input");
    By searchBtn = By.name("j_idt23:j_idt28");
    By accountTable = By.xpath("//div[@id='j_idt27']");

    WebDriver driver;

    public TransactionCustomerPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputAccountNumber(String accountNumber) {
        driver.findElement(accountNumberTextbox).sendKeys(accountNumber);
    }

    public void inputDateFrom(String dateFrom) {
        driver.findElement(dateFromTextbox).sendKeys(dateFrom);
    }

    public void inputDateTo(String dateTo) {
        driver.findElement(dateToTextbox).sendKeys(dateTo);
    }

    public void clickSearchBtn() {
        driver.findElement(searchBtn).click();
    }

    public List<WebElement> getAccountRows() {
        return driver.findElements(accountTable);
    }

   // private boolean isDateInRange(String dateChuyenTien, String dateFrom, String dateTo) {
    //    return (dateChuyenTien.equals(dateFrom)||dateChuyenTien.equals(dateTo)||
     //           (dateChuyenTien.after(dateFrom) && dateChuyenTien.before(dateTo)));
    //}

    public void inputSearchCustomer(String dateFrom, String dateTo) {
        inputDateFrom(dateFrom);
        inputDateTo(dateTo);
        clickSearchBtn();
    }
}
