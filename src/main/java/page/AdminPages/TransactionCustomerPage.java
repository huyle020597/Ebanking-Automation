package page.AdminPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


public class TransactionCustomerPage {
    By accountNumberTextbox = By.xpath("(//input[@id='j_idt23:mas'])[1]");
    By dateFromTextbox = By.id("j_idt23:mask_input");
    By dateToTextbox = By.id("j_idt23:masks_input");
    By searchBtn = By.name("j_idt23:j_idt28");
    By transactionTableRows = By.xpath("//table[@role='grid']");

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

    @Step ("Input Data To Search Box")
    public void search(String accountNumber, String dateFrom, String dateTo) {
        inputAccountNumber(accountNumber);
        inputDateFrom(dateFrom);
        inputDateTo(dateTo);
        clickSearchBtn();
    }

    private int getColumnOrder(String columnName) {
        return driver.findElements(By.xpath(String.format("//th[.='%s']/preceding-sibling::th", columnName))).size() + 1;
    }

    private int getSenderAccountColumnOrder() {
        return getColumnOrder("Tài Khoản người Gữi");
    }

    private int getTransactionDateColumnOrder() {
        return getColumnOrder("Date Chuyển tiền");
    }

    @Step ("Get data of each cell based on column order")
    public String getAccountNumbByRow(int rowNumber) {
        By cellLocator = By.xpath(String.format("//table[@role='grid']/tbody/tr[%d]/td[%s]", rowNumber - 1));
        return driver.findElements(cellLocator).get(getSenderAccountColumnOrder() - 1).getText();
    }

    @Step ("Get transaction date from selected row")
    public String getTransactionDateByRow(int rowNumber) {
            By cellLocator = By.xpath(String.format("//table[@role='grid']/tbody/tr[%d]/td[%s]", rowNumber));
        return driver.findElements(cellLocator).get(getTransactionDateColumnOrder() - 1).getText();
    }

    @Step ("Verify the displayed date matches the input date")
    public boolean isTransactionsDateBetween(String startDateinString, String endDateinString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean isWithinDateRange = true;
        try {
            //Convert String date to LocalDate
            LocalDate startDate = LocalDate.parse(startDateinString, formatter);
            LocalDate endDate = LocalDate.parse(endDateinString, formatter);

            List<WebElement> listTransactionDate = driver.findElements(By.xpath("//td[5]"));
            for (int i = 0; i < listTransactionDate.size(); i++) {
                LocalDate checkDate = LocalDate.parse(listTransactionDate.get(i).getText(), formatter);
                if (!(checkDate.isAfter(startDate)
                        && checkDate.isBefore(endDate)
                        || checkDate.isEqual(startDate)
                        || checkDate.isEqual(endDate))) {
                    isWithinDateRange = false;
                    break;
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày không hợp lệ!");
        }
        return isWithinDateRange;
    }

    @Step ("Verify the account number matches the input account number.")
    public boolean isTransactionAccountsValid (String accountNo) {
        List<WebElement> listTransactionAccounts = driver.findElements(By.xpath("//td[2]"));
        boolean isValid = true ;
        for (int i=3; i< listTransactionAccounts.size()-4;i++) {
            if (listTransactionAccounts.get(i).getText().equals(accountNo)) {
                isValid = true;
            } else {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}