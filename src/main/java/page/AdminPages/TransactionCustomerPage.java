package page.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionCustomerPage {
    By accountNumberTextbox = By.xpath("(//input[@id='j_idt23:mas'])[1]");
    By dateFromTextbox = By.id("j_idt23:mask_input");
    By dateToTextbox = By.id("j_idt23:masks_input");
    By searchBtn = By.name("j_idt23:j_idt28");

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

   // private boolean isDateInRange(String dateChuyenTien, String dateFrom, String dateTo) {
    //    return (dateChuyenTien.equals(dateFrom)||dateChuyenTien.equals(dateTo)||
     //           (dateChuyenTien.after(dateFrom) && dateChuyenTien.before(dateTo)));
    //}

    public void inputSearchCustomer(String accountNumber, String dateFrom, String dateTo) {
        inputAccountNumber(accountNumber);
        inputDateFrom(dateFrom);
        inputDateTo(dateTo);
        clickSearchBtn();
    }
}
