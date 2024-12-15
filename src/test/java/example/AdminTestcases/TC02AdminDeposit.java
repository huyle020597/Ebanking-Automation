package example.AdminTestcases;

import modal.Constants;
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


public class TC02AdminDeposit {
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
    double newReceiveBalance;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        loginAdminPage = new LoginAdminPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        depositPage = new DepositPage(driver);
        homePage = new HomePage(driver);
        depositAmount = 325712.0;

        driver.get(Constants.ADMIN_URL);
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
        //dang nhap tai khoan user
        loginPage.login(Constants.userId1, Constants.password1);

        //chon tai khoan va lay so du
        receiveAccountNo = bankAccountsPage.getAccountNoByIndex(1);
        bankAccountsPage.viewDetailsByIndex(1);
        receiveBalance = bankAccountsPage.getAccountBalance();


        //dang nhap voi tai khoan admin
        driver.switchTo().newWindow(WindowType.TAB);
      //  loginAdminPage.loginAdmin(Constants.adminId,Constants.adminPassword);
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
        loginAdminPage.loginAdmin("1","admin");


        //Nop tien va xac nhan
        homePage.openDepositPage();
        depositPage.inputReceiveAccount(receiveAccountNo);
        depositPage.inputAmount(depositAmount);
        depositPage.inputNote("test");
        depositPage.clickConfirm();
        softAssert.assertTrue(depositPage.isSuccessfulMsgDisplayed());


        //quay lai tab user kiem tra so du tai khoan
        driver.switchTo().window(originalHandle);
        bankAccountsPage.openAccountPage();
        bankAccountsPage.viewDetailsByAccNumber(receiveAccountNo);

        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), receiveBalance + depositAmount);

        softAssert.assertAll();
    }

}
