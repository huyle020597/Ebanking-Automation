package page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    By registerBtnLocator = By.linkText("Tạo tài khoản");
    By accountTbLocator = By.id("j_idt9:soucre");
    By passwordTbLocator = By.id("j_idt9:pwd1");
    By reenterPasswordTbLocator = By.id("j_idt9:pwd2");
    By fullNameTbLocator = By.id("j_idt9:key");
    By phoneTbLocator = By.id("j_idt9:phone");
    By dobTbLocator = By.id("j_idt9:mask_input");
    By genderMaleLocator = By.xpath("(//span[@class='ui-radiobutton-icon ui-icon ui-icon-blank'])[1]");
    By genderFemaleLocator = By.xpath("(//span[@class='ui-radiobutton-icon ui-icon ui-icon-blank'])[2]");
    By cityDropdownLocator = By.id("j_idt9:country_label");
    By idTbLocator = By.id("j_idt9:country_label");
    By emailTbLocator = By.id("j_idt9:email");
    By confirmBtnLocator = By.xpath("//span[@class='ui-button-text ui-c']");

    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickRegisterBtn() {
        driver.findElement(registerBtnLocator).click();
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
        driver.findElement(cityDropdownLocator);
    }

    public void enterIdUser(String id) {
        driver.findElement(idTbLocator).sendKeys(id);
    }

    public void enterEmail(String email) {
        driver.findElement(emailTbLocator).sendKeys(email);
    }

    public void clickConfirmBtn() {
        driver.findElement(confirmBtnLocator).click();
    }

    public void registerAccount(String account) {
        enterAccount(account);
    }

}

