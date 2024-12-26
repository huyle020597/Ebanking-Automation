package page.UserPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenBankAccountPage {
    By accountTypeLocator = By.cssSelector(".ui-selectonemenu-label.ui-inputfield.ui-corner-all");
    By nonTermAccountTypeLocator = By.cssSelector(".ui-selectonemenu-item.ui-selectonemenu-list-item.ui-corner-all.ui-state-highlight");
    By savingAccountTypeLocator = By.xpath("(//li[contains(@class, 'ui-selectonemenu-item') and contains(@class, 'ui-selectonemenu-list-item') and contains(@class, 'ui-corner-all')])[2]");
    By createAccBtnLocator = By.name("j_idt23:j_idt31");
    By successMessageLocator = By.xpath("//div[text()='Mở tài khoản thành công']");
    By closeMessageBtnLocator = By.xpath("//span[contains(@class, 'ui-icon') and contains(@class, 'ui-icon-closethick')]");

    WebDriver driver;


    public OpenBankAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAccTypeDropdown () {driver.findElement(accountTypeLocator).click();}

    public void selectNonTermAcc () {driver.findElement(nonTermAccountTypeLocator).click();};

    public void selectSavingAcc () {driver.findElement(savingAccountTypeLocator).click();};

    public void clickCreateAccBtn () {
        driver.findElement(createAccBtnLocator).click();
    }

    @Step("Open Saving Account")
    public void createSavingAccount () {
        clickAccTypeDropdown();
        selectSavingAcc();
        clickCreateAccBtn();
    }

    @Step ("Open NonTerm Account")
    public void createNonTermAccount () {
        clickAccTypeDropdown();
        selectNonTermAcc();
        clickCreateAccBtn();
    }


    @Step ("Verify if successful message displays")
    public boolean isSuccessfulMsgDisplayed () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
        return driver.findElement(successMessageLocator).isDisplayed();
    }

    @Step ("Close successful message")
    public void closeMessage () { driver.findElement(closeMessageBtnLocator).click();}

}
