package page.UserPages;

import io.qameta.allure.Step;
import model.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class YopmailPage {
    By emailBoxLocator = By.className("ycptinput");
    By receivingEmailsLocator = By.xpath("//button[@onclick='g(this);']");
    By accessEmailBtnLocator = By.xpath("//i[@class='material-icons-outlined f36']");
    By OTPcodeLocator = By.xpath("//div[@id='mail']/pre");
    By activatedURLLocator = By.xpath("//div[@id='mail']/pre");

    WebDriver driver;

    public YopmailPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Input email address")
    public void inputEmailAddress (String email) {
        driver.findElement(emailBoxLocator).sendKeys(email);
        driver.findElement(accessEmailBtnLocator).click();
    }

    public void openEmailByIndex (int index) {
        List <WebElement> listEmails = driver.findElements(receivingEmailsLocator);
        listEmails.get(index-1).click();
    }

    @Step ("Get OTP code")
    public String getOTPcode () {
        driver.switchTo().frame("ifmail");
        String OTPCode = driver.findElement(OTPcodeLocator).getText().replace("OTP:  ","");
        return OTPCode;
    }



    @Step ("Get OTP code by email")
    public String getOTPcodeByEmail (String senderEmailAddress) throws InterruptedException {
        Thread.sleep(1500);
        inputEmailAddress(senderEmailAddress);
        return getOTPcode();
    }

    @Step ("Get activate URL by email")
    public String getActivateURL(String email) throws InterruptedException {
        Thread.sleep(1500);
        inputEmailAddress(email);
        driver.switchTo().frame("ifmail");
        return driver.findElement(activatedURLLocator).getText();
    }
}

