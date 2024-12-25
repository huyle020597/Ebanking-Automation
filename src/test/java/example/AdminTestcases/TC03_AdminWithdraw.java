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

        withdrawAmount = faker.number().numberBetween(1,10)*1000;

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
        loginPage.login(Constants.USER_ID_1, Constants.USER_PASSWORD_1);

        //chon tai khoan va lay so du
        receiveAccountNo = bankAccountsPage.getAccountNoByIndex(1);
        bankAccountsPage.viewDetailsByIndex(1);
        receiveBalance = bankAccountsPage.getAccountBalance();
        // lấy số dư trừ một số random để đảm bảo số tiền withdraw không vuot quá

        //dang nhap voi tai khoan admin
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.ADMIN_URL);
        loginAdminPage.loginAdmin(Constants.ADMIN_ID, Constants.ADMIN_PASSWORD);

        //Rut tien va xac nhan
        homePage.openWithdrawPage();
        withdrawPage.withdraw(receiveAccountNo,withdrawAmount,"testing");
        softAssert.assertTrue(withdrawPage.isSuccessfulMsgDisplayed());

        //quay lai tab user kiem tra so du tai khoan
        driver.switchTo().window(originalHandle);
        bankAccountsPage.openAccountPage();
        bankAccountsPage.viewDetailsByAccNumber(receiveAccountNo);

        softAssert.assertEquals(bankAccountsPage.getAccountBalance(),receiveBalance - withdrawAmount);
    }
}
