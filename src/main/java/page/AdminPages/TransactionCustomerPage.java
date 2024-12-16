package page.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void search (String accountNumber,String dateFrom, String dateTo) {
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
        By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td", rowNumber - 1));
        return driver.findElements(cellLocator).get(getSenderAccountColumnOrder() - 1).getText();
    }

    public String getTransactionDateByRow(int rowNumber) {
        By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td", rowNumber - 1));
        return driver.findElements(cellLocator).get(getTransactionDateColumnOrder() - 1).getText();
    }


    public boolean isTransactionDateValid(String dateFrominString, String dateToinString, int rowNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean isValid = true;
        try {
            // Chuyển đổi chuỗi ngày thành đối tượng Date
            Date startDate = sdf.parse(dateFrominString);
            Date endDate = sdf.parse(dateToinString);
            Date checkDate = sdf.parse(getTransactionDateByRow(rowNumber));
            // Kiểm tra xem checkDate có nằm trong khoảng startDate và endDate không
            if( (checkDate.after(startDate) && checkDate.before(endDate)) || checkDate.equals(startDate) || checkDate.equals(endDate)  ){
                isValid = true;
            } else {
                isValid = false;
            }
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ!");
        }
        return isValid;
    }


}
