package page.AdminPages;

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
    By transactionTableRows = By.xpath("//table[@id='resultTable']/tbody/tr");

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

    //Lấy data của từng ô dựa vào thứ tự cột
    public String getAccountNumbByRow(int rowNumber) {
        // By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td[2]", rowNumber - 1));
        By cellLocator = By.xpath(String.format("//table[@id='resultTable']/tbody/tr[%d]/td[2]", rowNumber));
        //return driver.findElements(cellLocator).get(getSenderAccountColumnOrder() - 1).getText();
        return driver.findElement(cellLocator).getText();
    }

    public String getTransactionDateByRow(int rowNumber) {
//        By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td", rowNumber - 1));
//        return driver.findElements(cellLocator).get(getTransactionDateColumnOrder() - 1).getText();
        By cellLocator = By.xpath(String.format("//table[@id='resultTable']/tbody/tr[%d]/td[5]", rowNumber));
        return driver.findElement(cellLocator).getText();
    }

    public boolean isTransactionDateValid(String dateFrom, String dateTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(dateFrom, formatter);
        LocalDate endDate = LocalDate.parse(dateTo, formatter);

        List<WebElement> rows = driver.findElements(transactionTableRows);
        for (WebElement row : rows) {
            String transactionDateText = row.findElement(By.xpath("./td[5]")).getText();
            LocalDate transactionDate = LocalDate.parse(transactionDateText, formatter);
            if (transactionDate.isBefore(startDate) || transactionDate.isAfter(endDate)) {
                return false;
            }
        }
        return true;
    }

    public void iterateTransactionRows(TransactionRowProcessor processor) {
        //Duyệt qua từng hàng của bảng
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));
        for (int i = 1; i <= rows.size(); i++) {
            String senderAccount = getAccountNumbByRow(i);
            String transactionDate = getTransactionDateByRow(i);
            processor.processRow(i, senderAccount, transactionDate);
        }
    }

    public interface TransactionRowProcessor {
        void processRow(int rowIndex, String senderAccount, String transactionDate);
    }


//    public boolean isTransactionDateValid(String dateFrominString, String dateToinString) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        boolean isValid = true;
//        try {
//            // Chuyển ngày String thành LocalDate
//            LocalDate startDate = LocalDate.parse(dateFrominString, formatter);
//            LocalDate endDate = LocalDate.parse(dateToinString, formatter);
//
//            List<WebElement> listTransactionByCustomer = driver.findElements(By.xpath("//td[5]"));
//            for (int i = 0; i < 7; i++) {
//                LocalDate checkDate = LocalDate.parse(listTransactionByCustomer.get(i).getText(), formatter);
//                if (!(checkDate.isAfter(startDate)
//                        && checkDate.isBefore(endDate)
//                        || checkDate.isEqual(startDate)
//                        || checkDate.isEqual(endDate))) {
//                    isValid = false;
//                    break;
//                }
//            }
//        } catch (DateTimeParseException e) {
//            System.out.println("Định dạng ngày không hợp lệ!");
//        }
//        return isValid;
}


