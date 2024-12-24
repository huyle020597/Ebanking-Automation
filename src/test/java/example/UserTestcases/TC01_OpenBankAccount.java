package example.UserTestcases;

import model.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;
import page.UserPages.OpenBankAccountPage;

public class TC01_OpenBankAccount {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    OpenBankAccountPage openBankAccountPage;
    BankAccountsPage bankAccountsPage;
    int previousAccountQuantity;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        openBankAccountPage = new OpenBankAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void openNewAccount() {
        loginPage.login(Constants.USER_1);
// Get account quantity before creating a new one
        bankAccountsPage.openAccountPage();
        previousAccountQuantity = bankAccountsPage.getQuantityAccount();

// Create Saving Account
        bankAccountsPage.openOpenAccountPage();
        openBankAccountPage.createSavingAccount();

// Verify if the successful message display
        softAssert.assertTrue(openBankAccountPage.isSuccessfulMsgDisplayed());
        openBankAccountPage.closeMessage();

// Verify if the account quantity has increased by 1
        softAssert.assertEquals(bankAccountsPage.getQuantityAccount(), previousAccountQuantity + 1);
        softAssert.assertAll();
    }
}
