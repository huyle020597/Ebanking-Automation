package page.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CustomerList {
    By inputCustomerIDTextbox = By.id("j_idt22:j_idt24:j_idt25:filter");
    By inputFullNameTextbox = By.id("j_idt22:j_idt24:j_idt27:filter");
    By inputAddressTextbox = By.id("j_idt22:j_idt24:j_idt29:filter");
    By inputCMDNTextbox = By.id("j_idt22:j_idt24:j_idt32:filter");
    By inputPhoneNumberTextbox = By.id("j_idt22:j_idt24:j_idt34:filter");
    By inputActiveTextbox = By.id("j_idt22:j_idt24:j_idt36:filter");
    By inputStatusTextbox = By.id("j_idt22:j_idt24:j_idt39:filter");
    By customerIdReturn = By.xpath("(//td[@role='gridcell'])[1]");
    By customerPhoneReturn;
    //td[@role='gridcell'][normalize-space()='7774003855'])[1]

    WebDriver driver;

    public CustomerList(WebDriver driver) {
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

    public String customerIdSearchReturn(String customerId) {
       // customerId = By.xpath(String.format(""), customerId);
        driver.findElement(customerIdReturn).getText();
        return customerId;
    }

    public String phoneNumberSearchReturn(String phoneNumber) {
        List<WebElement> phoneElements = driver.findElements(By.xpath(String.format("//td[@role='gridcell'][text()='%s']", phoneNumber)));
        if (!phoneElements.isEmpty()) {
            // Lấy ngẫu nhiên 1 phần tử từ danh sách kết quả
            int randomIndex = (int) (Math.random() * phoneElements.size());
            String randomPhone = phoneElements.get(randomIndex).getText();
            return randomPhone;
        } else {
            return null; // Trường hợp không tìm thấy kết quả nào
        }
    }

    public void enterActive(String active) {
        driver.findElement(inputActiveTextbox).sendKeys(active);
    }

    public void enterStatus(String status) {
        driver.findElement(inputStatusTextbox).sendKeys(status);
    }

    public void customerList(String Id, String phoneNumber) {
        enterCustomerID(Id);
        enterPhoneNumber(phoneNumber);
    }

}
