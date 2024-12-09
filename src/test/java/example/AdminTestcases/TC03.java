package example.AdminTestcases;

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
import page.OpenAccountPage;

public class TC03 {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    OpenAccountPage openAccountPage;
    BankAccountsPage bankAccountsPage;
    page.AdminPages.LoginPage loginPage2;
    int accountQuantityBefore;
    WithdrawPage withdrawPage;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        loginPage2 = new page.AdminPages.LoginPage(driver);
        openAccountPage = new OpenAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        withdrawPage = new WithdrawPage(driver);

        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#");
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void loginUserAccount() {
        loginPage.login("huyle020597", "Maddie123@");

//Kiem tra thong bao cos hien thi
        softAssert.assertTrue(withdrawPage.isSuccessfulMsgDisplayed());
    }
}
