package example.AdminTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import org.openqa.selenium.WindowType;
import org.testng.annotations.AfterMethod;
import page.AdminPages.HomePage;
import page.AdminPages.LoginAdminPage;
import page.AdminPages.TransactionCustomerPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class TC11_ViewTransaction {
    WebDriver driver;
    SoftAssert softAssert;
    Faker faker;

    LoginAdminPage loginAdminPage;
    LoginPage loginPage;

    HomePage homePage;
    TransactionCustomerPage transactionCustomerPage;
    BankAccountsPage bankAccountsPage;

    DateTimeFormatter formatter;
    Date startDate;
    LocalDate startLocalDate;
    LocalDate endLocalDate;
    LocalDate randomDate1;
    LocalDate randomDate2;
    String dateFrominString;
    String dateToinString;

    String accountNo;
    String originalHandle;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        softAssert = new SoftAssert();
        faker = new Faker();

        loginAdminPage = new LoginAdminPage(driver);
        loginPage = new LoginPage(driver);

        homePage = new HomePage(driver);
        transactionCustomerPage = new TransactionCustomerPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);

        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        randomDate1 = LocalDate.of(2024, 12, 10);
        randomDate2 = LocalDate.of(2024, 12, 15);
        startDate = faker.date().between(Date.from(randomDate1.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(randomDate2.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endLocalDate = startLocalDate.plusDays(5);
        dateFrominString = formatter.format(startLocalDate);
        dateToinString = formatter.format(endLocalDate);

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        originalHandle = driver.getWindowHandle();

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - View transaction list by Account")
    public void viewTransactionByAccount() {
        //Step 1: Login with user account and get account number
        loginPage.login(Constants.USER_1);
        accountNo = bankAccountsPage.getAccountNoByIndex(2);

        //Step 2: Switch tab and login with admin account
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.ADMIN_URL);
        loginAdminPage.loginAdmin(Constants.ADMIN);

        //Step 3: Input data want to view transactions
        homePage.openCustomerTransactionListPage();
        transactionCustomerPage.search(accountNo, dateFrominString, dateToinString);

        //Step 4: Check that expected information matches actual information
        softAssert.assertTrue(transactionCustomerPage.isTransactionAccountsValid(accountNo));
        softAssert.assertTrue(transactionCustomerPage.isTransactionsDateBetween(dateFrominString, dateToinString));
        softAssert.assertAll();
    }
}

