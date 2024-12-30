package page.AdminPages;

import io.qameta.allure.Step;
import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CustomerListPage {
    By inputCustomerIDTextbox = By.id("j_idt22:j_idt24:j_idt25:filter");
    By inputFullNameTextbox = By.id("j_idt22:j_idt24:j_idt27:filter");
    By inputAddressTextbox = By.id("j_idt22:j_idt24:j_idt29:filter");
    By inputCMDNTextbox = By.id("j_idt22:j_idt24:j_idt32:filter");
    By inputPhoneNumberTextbox = By.id("j_idt22:j_idt24:j_idt34:filter");
    By customerTableRows = By.xpath("//tbody[@id='j_idt22:j_idt24_data']/tr"); // Table rows, sua lai locator


    WebDriver driver;

    public CustomerListPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterCustomerID(String Id) {
        driver.findElement(inputCustomerIDTextbox).sendKeys(Id);
    }

    public void enterFullName(String fullName) {
        driver.findElement(inputFullNameTextbox).sendKeys(fullName);
    }

    public void enterAddress(String address) {
        driver.findElement(inputAddressTextbox).sendKeys(address);
    }

    public void enterCMDN(String cmdn) {
        driver.findElement(inputCMDNTextbox).sendKeys(cmdn);
    }

    public void enterPhoneNumber(String phoneNumber) {
        driver.findElement(inputPhoneNumberTextbox).sendKeys(phoneNumber);
    }

    @Step("Get Customer Data From Row Index")
    public User getCustomerDataFromRow(int rowIndex) {
        List<WebElement> rows = driver.findElements(customerTableRows);
            WebElement selectedRow = rows.get(rowIndex - 1);
            List<WebElement> cells = selectedRow.findElements(By.tagName("td"));

            User selectedUser = new User(cells.get(0).getText(), null, null);
            selectedUser.setFullName(cells.get(1).getText());
            selectedUser.setCmnd(cells.get(3).getText());
            selectedUser.setPhoneNumber(cells.get(4).getText());
            selectedUser.setCity(cells.get(2).getText());
            return selectedUser;
        }
    }

