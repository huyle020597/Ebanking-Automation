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
    boolean isDateBetween;


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
        randomDate1 = LocalDate.of(2024, 12, 1);
        randomDate2 = LocalDate.of(2024, 12, 23);
        startDate = faker.date().between(Date.from(randomDate1.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(randomDate2.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endLocalDate = startLocalDate.plusDays(10);
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
        //Login user account, select account number and get balance
        loginPage.login(Constants.USER_1);
        accountNo = bankAccountsPage.getAccountNoByIndex(1);

        //Login admin account
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.ADMIN_URL);
        loginAdminPage.loginAdmin(Constants.ADMIN_ID, Constants.ADMIN_PASSWORD);

        //Input data
        homePage.openCustomerTransactionListPage();
        transactionCustomerPage.search(accountNo, dateFrominString, dateToinString);



        // kiem tra thong tin hien thi
        softAssert.assertTrue(transactionCustomerPage.isTransactionAccountsValid(accountNo));
        softAssert.assertTrue(transactionCustomerPage.isTransactionsDateBetween(dateFrominString, dateToinString));

        softAssert.assertAll();
    }
}

