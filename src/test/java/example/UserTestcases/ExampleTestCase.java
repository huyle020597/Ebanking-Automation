package example.UserTestcases;

import modal.BankAccount;
import modal.User;
import model.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;
import page.UserPages.MenuBar;
import page.UserPages.OpenBankAccountPage;

public class ExampleTestCase {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    OpenBankAccountPage openBankAccountPage;
    BankAccountsPage bankAccountsPage;
    BankAccount bankAccount;
    int previousAccountQuantity;
    User user1;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        openBankAccountPage = new OpenBankAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        user1 = new User(Constants.USER_ID_1, Constants.USER_PASSWORD_1,"100001284");

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void openNewAccount() {
        loginPage.login(user1);
// Lay so luong account truoc khi tao tai khoan
        bankAccountsPage.openAccountPage();
        previousAccountQuantity = bankAccountsPage.getQuantityAccount();

// Tao tai khoan Saving
        bankAccountsPage.openOpenAccountPage();
        openBankAccountPage.createSavingAccount();

// Kiem tra thong bao co hien thi
        softAssert.assertTrue(openBankAccountPage.isSuccessfulMsgDisplayed());
        openBankAccountPage.closeMessage();

// Kiem tra so luong account sau khi tao tai khoan
        softAssert.assertEquals(bankAccountsPage.getQuantityAccount(), previousAccountQuantity + 1);
        softAssert.assertAll();
    }
}
