package example.AdminTestcases;

import org.openqa.selenium.WindowType;
import page.AdminPages.HomePage;
import page.AdminPages.WithdrawPage;
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

import java.time.Duration;

public class TC03AdminWithdraw {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    HomePage homePage;
    OpenBankAccountPage openBankAccountPage;
    BankAccountsPage bankAccountsPage;
    page.AdminPages.LoginPage loginPageAdmin;
    WithdrawPage withdrawPage;
    String userId1;
    String password1;
    String adminId;
    String adminPassword;
    double withdrawAmount;
    String originalHandle;
    String receiveAccountNo;
    double receiveBalance;
    double newReceiveBalance;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        homePage = new HomePage(driver);
        loginPageAdmin = new page.AdminPages.LoginPage(driver);
        openBankAccountPage = new OpenBankAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        withdrawPage = new WithdrawPage(driver);
        userId1 = "huyle020597";
        password1 = "Maddie123@";
        adminId = "1";
        adminPassword = "admin";
        withdrawAmount = 325712.0;

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        originalHandle = driver.getWindowHandle();
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - Withdraw money successfully")
    public void loginUserAccount() {
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/index.xhtml");
        loginPage.login(userId1, password1);

        //chon tai khoan va lay so du
        receiveAccountNo = bankAccountsPage.getAccountNoByIndex(1);
        bankAccountsPage.viewDetailsByIndex(1);
        receiveBalance = bankAccountsPage.getAccountBalance();

        //dang nhap voi tai khoan admin
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
        loginPageAdmin.loginAdmin(adminId, adminPassword);

        //Rut tien va xac nhan
        homePage.openWithdrawPage();
        withdrawPage.inputAccountNumber(receiveAccountNo);
        withdrawPage.inputAmount(String.valueOf(withdrawAmount));
        withdrawPage.inputNote("test");
        withdrawPage.clickTransfer();
        softAssert.assertTrue(withdrawPage.isSuccessfulMsgDisplayed());

        //quay lai tab user kiem tra so du tai khoan
        driver.switchTo().window(originalHandle);
        bankAccountsPage.openAccountPage();
        bankAccountsPage.viewDetailsByAccNumber(receiveAccountNo);
        newReceiveBalance = bankAccountsPage.getAccountBalance();

        softAssert.assertEquals(newReceiveBalance,receiveBalance - withdrawAmount);
    }
}
