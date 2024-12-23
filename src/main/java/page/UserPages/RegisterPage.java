package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    By accountTbLocator = By.id("j_idt9:soucre");
    By passwordTbLocator = By.id("j_idt9:pwd1");
    By reenterPasswordTbLocator = By.id("j_idt9:pwd2");
    By fullNameTbLocator = By.id("j_idt9:key");
    By phoneTbLocator = By.id("j_idt9:phone");
    By dobTbLocator = By.id("j_idt9:mask_input");
    By genderMaleLocator = By.xpath("(//span[@class='ui-radiobutton-icon ui-icon ui-icon-blank'])[1]");
    By genderFemaleLocator = By.xpath("(//span[@class='ui-radiobutton-icon ui-icon ui-icon-blank'])[2]");
    By cityDropdownLocator = By.id("j_idt9:country_label");
    By idTbLocator = By.id("j_idt9:cmnd");
    By emailTbLocator = By.id("j_idt9:email");
    By confirmBtnLocator = By.xpath("//span[@class='ui-button-text ui-c']");
    By availableCityLocator;
    By createAccountBtnConfirmLocator = By.name("j_idt9:j_idt30");
    By msgConfirmCreateAccount = By.cssSelector(".ui-dialog-content.ui-widget-content");
    By closeMsgConfirm = By.cssSelector(".ui-icon.ui-icon-closethick");


    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }


    public void enterAccount(String account) {
        driver.findElement(accountTbLocator).sendKeys(account);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordTbLocator).sendKeys(password);
    }

    public void reenterPassword(String password2) {
        driver.findElement(reenterPasswordTbLocator).sendKeys(password2);
    }

    public void enterFullName (String fullName) {
        driver.findElement(fullNameTbLocator).sendKeys(fullName);
    }

    public void enterPhoneNumber(String phoneNumber) {
        driver.findElement(phoneTbLocator).sendKeys(phoneNumber);
    }

    public void enterDob (String dob) {
        driver.findElement(dobTbLocator).sendKeys(dob);
    }

    public void clickFemaleGender() {
        driver.findElement(genderFemaleLocator).click();
    }

    public void clickMaleGender() {
        driver.findElement(genderMaleLocator).click();
    }

    public void enterCity() {
        driver.findElement(cityDropdownLocator).click();
    }

    private void selectCity(String city) {
        enterCity();
        availableCityLocator = By.xpath(String.format("//li[contains(@class, 'ui-selectonemenu-item') and @data-label='%s']", city));
        driver.findElement(availableCityLocator).click();
    }

    public void enterIdUser(String id) {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(idTbLocator));
        driver.findElement(idTbLocator).sendKeys(id);

    }

    public void enterEmail(String email) {
        driver.findElement(emailTbLocator).sendKeys(email);
    }

    public void clickConfirmBtn() {
        driver.findElement(confirmBtnLocator).click();
    }

    public void registerAccount(String account, String password, String password2, String fullName, String phoneNumber,
                                String dob, String city, String id, String email) {
        enterAccount(account);
        enterPassword(password);
        reenterPassword(password2);
        enterFullName(fullName);
        enterPhoneNumber(phoneNumber);
        enterDob(dob);
        clickFemaleGender();
        selectCity(city);
        enterIdUser(id);
        enterEmail(email);
        clickConfirmBtn();
        clickCreateAccount();
        clickCloseMsg();
    }

    public void clickCreateAccount() {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(createAccountBtnConfirmLocator)));

        driver.findElement(createAccountBtnConfirmLocator).click();
    }

    public void msgConfirm() {
        driver.findElement(msgConfirmCreateAccount).click();
    }

    public void clickCloseMsg() {
        driver.findElement(closeMsgConfirm).click();
    }

    public boolean isSuccessfulMsgDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(msgConfirmCreateAccount));
        return driver.findElement(msgConfirmCreateAccount).isDisplayed();
    }
}

