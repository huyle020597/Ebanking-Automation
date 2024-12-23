package page.UserPages;

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

    public void inputEmailAddress (String email) {
        driver.findElement(emailBoxLocator).sendKeys(email);
        driver.findElement(accessEmailBtnLocator).click();
    }

    public void openEmailByIndex (int index) {
        List <WebElement> listEmails = driver.findElements(receivingEmailsLocator);
        listEmails.get(index-1).click();
    }

    public String getOTPcode () {
        driver.switchTo().frame("ifmail");
        String OTPCode = driver.findElement(OTPcodeLocator).getText().replace("OTP:  ","");
        return OTPCode;
    }



    public String getOTPcodeByEmail (String senderEmailAddress) throws InterruptedException {
        Thread.sleep(2000);
        inputEmailAddress(senderEmailAddress);
        return getOTPcode();
    }

    public String getActivateURL(String email) throws InterruptedException {
        Thread.sleep(2000);
        inputEmailAddress(email);
        driver.switchTo().frame("ifmail");
        return driver.findElement(activatedURLLocator).getText();
    }
}

