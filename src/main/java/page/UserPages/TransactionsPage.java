package page.UserPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class TransactionsPage {
    By accountsDropdownLocator = By.id("j_idt23:j_idt27_label");
    By availableAccountsLocator;
    By fromDateLocator = By.xpath("//input[@id='j_idt23:mask_input']");
    By toDateLocator = By.xpath("//input[@id='j_idt23:masks_input']");
    By searchBtnLocator = By.xpath("//input[@type='submit']");
    By selectedCalendarDate = By.xpath("//a[contains (@class, 'ui-state-active')]");


    By mainAccountColumnLocator = By.xpath("//th[.='Tài khoản  Chính']");
    WebDriver driver;

    public TransactionsPage(WebDriver driver) {
        this.driver = driver;
    }

    private void clickAccountsDropdown() {
        driver.findElement(accountsDropdownLocator).click();
    }

    @Step("Select an account")
    public void selectAccountByAccNumber(String accountNumber) {
        clickAccountsDropdown();
        availableAccountsLocator = By.xpath(String.format("//li[contains(@class, 'ui-selectonemenu-item')][@data-label='%s']", accountNumber));
        driver.findElement(availableAccountsLocator).click();
    }

    public void inputFromDate(String startDate) {
        driver.findElement(fromDateLocator).sendKeys(startDate);
    }

    public void inputToDate(String endDate) {
        driver.findElement(toDateLocator).sendKeys(endDate);
    }

    @Step ("Search transactions of a account number in a date range")
    public void searchTransaction(String accountNumber,String startDate, String endDate) {
        selectAccountByAccNumber(accountNumber);
        inputFromDate(startDate);
        inputToDate(endDate);
        selectDateInCalendar();
        clickSearchBtn();
    }

    public void clickSearchBtn() {
        driver.findElement(searchBtnLocator).click();
    }

    // Lấy số thứ tự của cột
    private int getColumnOrder(String columnName) {
        return driver.findElements(By.xpath(String.format("//th[.='%s']/preceding-sibling::th", columnName))).size() + 1;
    }

    private int getMainAccountColumnOrder() {
        return getColumnOrder("Tài khoản  Chính");
    }

    private int getTransactionDateColumnOrder() {
        return getColumnOrder("Ngày giao dịch");
    }


    public String getAccountNumbByRow(int rowNumber) {
        By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td", rowNumber - 1));
        return driver.findElements(cellLocator).get(getMainAccountColumnOrder() - 1).getText();
    }

    public String getTransactionDateByRow(int rowNumber) {
        By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td", rowNumber - 1));
        return driver.findElements(cellLocator).get(getTransactionDateColumnOrder() - 1).getText();
    }

    @Step ("Verify if all transaction date is within the date range input")
    public boolean isTransactionsDateBetween(String startDateinString, String endDateinString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            // Chuyển ngày String thành LocalDate
            LocalDate startDate = LocalDate.parse(startDateinString, formatter);
            LocalDate endDate = LocalDate.parse(endDateinString, formatter);

            List<WebElement> listTransactionDate = driver.findElements(By.xpath("//td[5]"));
            for (int i = 0; i < listTransactionDate.size(); i++) {
                LocalDate checkDate = LocalDate.parse(listTransactionDate.get(i).getText(), formatter);
                if (checkDate.isBefore(startDate) || checkDate.isAfter(endDate)) {
                    return false;
                }
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày không hợp lệ!");
            return false;
        }
    }

    public void selectDateInCalendar () {
        driver.findElement(selectedCalendarDate).click();
    }

    @Step ("Verify if all transaction accounts match the account selected")
    public boolean isTransactionAccountsValid (String accountNumber) {
        List<WebElement> listTransactionAccounts = driver.findElements(By.xpath("//td[1]"));
        boolean isValid = true ;
        for (int i=4; i<listTransactionAccounts.size()-4;i++) {
            if (listTransactionAccounts.get(i).getText().equals(accountNumber)) {
                isValid = true;
            } else {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
