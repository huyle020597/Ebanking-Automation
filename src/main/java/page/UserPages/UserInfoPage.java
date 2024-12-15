package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserInfoPage {
    By addressBoxLocator = By.id("j_idt24:j_idt36");
    By saveInfoBtnLocator = By.name("j_idt24:j_idt39");
    By userPhoneNumbLocator = By.id("j_idt24:j_idt34");

    WebDriver driver;

    public UserInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAddressValue () {
        return driver.findElement(addressBoxLocator).getAttribute("value");
    }
    public void clickSaveBtn() {
        driver.findElement(saveInfoBtnLocator).click();
    }
    public void enterAddress(String address) {
        driver.findElement(addressBoxLocator).sendKeys(address);
    }

    public void editAddress (String address) {
        driver.findElement(addressBoxLocator).clear();
        enterAddress(address);
        clickSaveBtn();
    }

    public String getUserPhoneNumber() {
        return driver.findElement(userPhoneNumbLocator).getText();
    }
}
