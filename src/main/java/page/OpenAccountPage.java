package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenAccountPage {
    By accountTypeLocator = By.cssSelector(".ui-selectonemenu-label.ui-inputfield.ui-corner-all");
    By nonTermAccountTypeLocator = By.cssSelector(".ui-selectonemenu-item.ui-selectonemenu-list-item.ui-corner-all.ui-state-highlight");
    By savingAccountTypeLocator = By.xpath("(//li[contains(@class, 'ui-selectonemenu-item') and contains(@class, 'ui-selectonemenu-list-item') and contains(@class, 'ui-corner-all')])[2]");
    By createAccBtnLocator = By.name("j_idt23:j_idt31");
    By successMessageLocator = By.xpath("//div[text()='Mở tài khoản thành công']");

    WebDriver driver;


    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAccTypeDropdown () {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(accountTypeLocator)));
        driver.findElement(accountTypeLocator).click();}

    public void selectNonTermAcc () {driver.findElement(nonTermAccountTypeLocator).click();};

    public void selectSavingAcc () {driver.findElement(savingAccountTypeLocator).click();};

    public void clickCreateAccBtn () {
        driver.findElement(createAccBtnLocator).click();
    }

    public boolean isSuccessfulMsgDisplayed () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
        return driver.findElement(successMessageLocator).isDisplayed();
    }

}
