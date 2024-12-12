package example.AdminTestcases;

import page.AdminPages.DepositPage;
import page.AdminPages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;
import page.UserPages.MenuBar;

import java.time.Duration;


public class TC02 {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    BankAccountsPage bankAccountsPage;
    page.AdminPages.LoginPage loginPage2;
    DepositPage depositPage;
    HomePage homePage;
    String userId1;
    String password1;
    String adminId;
    String adminPassword;
    Double depositAmount;
    //  LinkPage linkPage;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        loginPage2 = new page.AdminPages.LoginPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        depositPage = new DepositPage(driver);
        homePage = new HomePage(driver);
        userId1 = "huyle020597";
        password1 = "Maddie123@";
        adminId = "1";
        adminPassword = "admin";
        depositAmount = 325712.0;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - Deposit money into account successfully")
    public void depositMoney() {
        //dang nhap tai khoan user
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/index.xhtml");
        loginPage.login(userId1, password1);

        //chon tai khoan va lay so du
        bankAccountsPage.getAccountNoByIndex(1);
        bankAccountsPage.viewDetailsByIndex(1);
        bankAccountsPage.getAccountBalance();

        String originalHandle = driver.getWindowHandle();


        //dang nhap voi tai khoan admin
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
        loginPage2.loginAdmin(adminId, adminPassword);


        //Nop tien va xac nhan
        homePage.openDepositPage();
        depositPage.inputReceiveAccount("100001283"); //làm sao de dán kết quả da getText vao day??
        depositPage.inputAmount(depositAmount);
        depositPage.inputNote("test");
        depositPage.clickConfirm();
        softAssert.assertTrue(depositPage.isSuccessfulMsgDisplayed());


        //quay lai tab user kiem tra so du tai khoan
        driver.switchTo().window(originalHandle);
        bankAccountsPage.openAccountPage();
        bankAccountsPage.getAccountNoByIndex(1);
        bankAccountsPage.viewDetailsByIndex(1);
        bankAccountsPage.getAccountBalance();

      //  softAssert.assertEquals(bankAccountsPage.getAccountBalance(), + depositAmount);

        // Kiểm tra số dư tài khoản đã tăng lên sau khi gửi tiền
//        double initialBalanceAmount = Double.parseDouble(initialBalance.replaceAll("[^\\d.]", ""));
//        double depositAmount;
//        double updatedBalanceAmount = Double.parseDouble(updatedBalance.replaceAll("[^\\d.]", ""));
//        softAssert.assertEquals(updatedBalanceAmount, initialBalanceAmount + depositAmount, "Balance did not increase correctly");


        softAssert.assertAll();
    }

}
