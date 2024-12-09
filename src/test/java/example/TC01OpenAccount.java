package example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.BankAccountsPage;
import page.LoginPage;
import page.MenuBar;
import page.OpenBankAccountPage;

public class TC01OpenAccount {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    OpenBankAccountPage openBankAccountPage;
    BankAccountsPage bankAccountsPage;
    int accountQuantityBefore;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        openBankAccountPage = new OpenBankAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);

        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#");
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void openNewAccount() {
        loginPage.login("huyle020597", "Maddie123@");
// Lay so luong account truoc khi tao tai khoan
        bankAccountsPage.openAccountPage();
        accountQuantityBefore = bankAccountsPage.getQuantityAccount();

// Tao tai khoan Saving
        bankAccountsPage.openOpenAccountPage();
        openBankAccountPage.createSavingAccount();

// Kiem tra thong bao co hien thi
        softAssert.assertTrue(openBankAccountPage.isSuccessfulMsgDisplayed());
        openBankAccountPage.closeMessage();

// Kiem tra so luong account sau khi tao tai khoan
        softAssert.assertEquals(bankAccountsPage.getQuantityAccount(), accountQuantityBefore + 1);
        softAssert.assertAll();
    }
}
