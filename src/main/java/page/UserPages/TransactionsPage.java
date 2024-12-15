package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionsPage {
    By accountsDropdownLocator = By.id("j_idt23:j_idt27_label");
    By availableAccountsLocator;
    By fromDateLocator = By.xpath("//input[@id='j_idt23:mask_input']");
    By toDateLocator = By.xpath("//input[@id='j_idt23:masks_input']");
    By searchBtnLocator = By.xpath("//input[@type='submit']");


    By mainAccountColumnLocator = By.xpath("//th[.='Tài khoản  Chính']");
    WebDriver driver;

    public TransactionsPage(WebDriver driver) {
        this.driver = driver;
    }

    private void clickAccountsDropdown() {
        driver.findElement(accountsDropdownLocator).click();
    }

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

    public void inputDateRange(String startDate, String endDate) {
        inputFromDate(startDate);
        inputToDate(endDate);
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

    //Lấy data của từng ô dựa vào thứ tự cột
    public String getAccountNumbByRow(int rowNumber) {
        By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td", rowNumber - 1));
        return driver.findElements(cellLocator).get(getMainAccountColumnOrder() - 1).getText();
    }

    public String getTransactionDateByRow(int rowNumber) {
        By cellLocator = By.xpath(String.format("//tr[@data-ri='%s']/td", rowNumber - 1));
        return driver.findElements(cellLocator).get(getTransactionDateColumnOrder() - 1).getText();
    }

    public boolean isTransactionDateValid(String startDateinString, String endDateinString, int rowNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean isValid = true;
        try {
            // Chuyển đổi chuỗi ngày thành đối tượng Date
            Date startDate = sdf.parse(startDateinString);
            Date endDate = sdf.parse(endDateinString);
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
