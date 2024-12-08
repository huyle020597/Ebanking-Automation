package example.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionByDatePage {
    By dateFromTextbox = By.id("j_idt23:mask_input");
    By dateToTextbox = By.id("j_idt23:masks_input");
    By searchBtn = By.name("j_idt23:j_idt27");

    WebDriver driver;

    public TransactionByDatePage(WebDriver driver) {
        this.driver = driver;
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

}
