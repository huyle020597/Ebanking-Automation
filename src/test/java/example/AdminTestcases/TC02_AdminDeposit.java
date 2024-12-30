package example.AdminTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import page.AdminPages.DepositPage;
import page.AdminPages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.AdminPages.LoginAdminPage;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;
import page.UserPages.MenuBar;

import java.time.Duration;


public class TC02_AdminDeposit {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    BankAccountsPage bankAccountsPage;
    LoginAdminPage loginAdminPage;
    DepositPage depositPage;
    HomePage homePage;
    double depositAmount;
    String originalHandle;
    double receiveBalance;
    String receiveAccountNo;
    Faker faker;
    double newReceiveBalance;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        faker = new Faker();
        menuBar = new MenuBar(driver);
        loginAdminPage = new LoginAdminPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        depositPage = new DepositPage(driver);
        homePage = new HomePage(driver);
        depositAmount = faker.number().numberBetween(1,10)*1000;

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        originalHandle = driver.getWindowHandle();
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - Deposit money into account successfully")
    public void depositMoney() {
        //Step 1: Login with user account
        loginPage.login(Constants.USER_1);

        //Step 2: Select account and get balance
        receiveAccountNo = bankAccountsPage.getAccountNoByIndex(3);
        bankAccountsPage.viewDetailsByIndex(3);
        receiveBalance = bankAccountsPage.getAccountBalance();

        //Step 3: Switch tab and login with admin account
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.ADMIN_URL);
        loginAdminPage.loginAdmin(Constants.ADMIN);

        //Step 4: Deposit and confirm
        homePage.openDepositPage();
        depositPage.inputDeposit(receiveAccountNo,depositAmount,"testing");
        softAssert.assertTrue(depositPage.isSuccessfulMsgDisplayed());

        //Step 5: Go back to user tab and check account balance
        driver.switchTo().window(originalHandle);
        bankAccountsPage.openAccountPage();
        bankAccountsPage.viewDetailsByAccNumber(receiveAccountNo);

        //Step 6: Check that expected information matches actual information
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), receiveBalance + depositAmount);
        softAssert.assertAll();
    }

}
