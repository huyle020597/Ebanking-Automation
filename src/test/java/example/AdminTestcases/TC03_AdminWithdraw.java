package example.AdminTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import org.openqa.selenium.WindowType;
import page.AdminPages.HomePage;
import page.AdminPages.LoginAdminPage;
import page.AdminPages.WithdrawPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;

import java.time.Duration;

public class TC03_AdminWithdraw {
    WebDriver driver;
    SoftAssert softAssert;
    Faker faker;

    LoginPage loginPage;
    LoginAdminPage loginAdminPage;
    HomePage homePage;
    BankAccountsPage bankAccountsPage;
    WithdrawPage withdrawPage;

    double withdrawAmount;
    String originalHandle;
    String receiveAccountNo;
    double receiveBalance;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        softAssert = new SoftAssert();
        faker = new Faker();
        loginPage = new LoginPage(driver);
        loginAdminPage = new LoginAdminPage(driver);
        homePage = new HomePage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        withdrawPage = new WithdrawPage(driver);
        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        originalHandle = driver.getWindowHandle();
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - Withdraw money successfully")
    public void TC03() {
        //Step 1: Login with user account
        loginPage.login(Constants.USER_1);

        //Step 2: Select account and get balance
        receiveAccountNo = bankAccountsPage.getAccountNoByIndex(3);
        bankAccountsPage.viewDetailsByIndex(3);
        receiveBalance = bankAccountsPage.getAccountBalance();
        withdrawAmount = faker.number().numberBetween(0L, (long) (receiveBalance-Constants.WITHDRAW_FEE));

        //Step 3: Switch tab and login with admin account
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.ADMIN_URL);
        loginAdminPage.loginAdmin(Constants.ADMIN);

        //Step 4: Withdraw and confirm
        homePage.openWithdrawPage();
        withdrawPage.withdraw(receiveAccountNo,withdrawAmount,"testing");
        softAssert.assertTrue(withdrawPage.isSuccessfulMsgDisplayed());

        //Step 5: Go back to user tab and check account balance
        driver.switchTo().window(originalHandle);
        bankAccountsPage.openAccountPage();
        bankAccountsPage.viewDetailsByAccNumber(receiveAccountNo);

        //Step 6: Check that expected information matches actual information
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(),receiveBalance - withdrawAmount);
        softAssert.assertAll();
    }
}
