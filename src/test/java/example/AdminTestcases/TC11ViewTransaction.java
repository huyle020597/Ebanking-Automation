package example.AdminTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import org.openqa.selenium.WindowType;
import page.AdminPages.HomePage;
import page.AdminPages.LoginAdminPage;
import page.AdminPages.TransactionCustomerPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class TC11ViewTransaction {
    WebDriver driver;
    SoftAssert softAssert;
    Faker faker;

    LoginAdminPage loginAdminPage;
    LoginPage loginPage;

    HomePage homePage;
    TransactionCustomerPage transactionCustomerPage;
    BankAccountsPage bankAccountsPage;

    SimpleDateFormat formatter;
    Date dateFrom;
    Date dateTo;
    Date startDate;
    Date endDate;
    String dateFrominString;
    String dateToinString;

    String accountNo;
    String originalHandle;
    String selectedAccount;
    long maxRange;
    long randomDuration;



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

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = Date.from(LocalDate.of(2024,11,10).atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateTo = Date.from(LocalDate.of(2024,12,15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        startDate = faker.date().between(dateFrom,dateTo);
        maxRange = dateTo.getTime() - startDate.getTime();
        randomDuration = (long) (Math.random() * maxRange);
        endDate = new Date(startDate.getTime() + randomDuration);


        dateFrominString = formatter.format(startDate);
        dateToinString = formatter.format(endDate);


        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        originalHandle = driver.getWindowHandle();


        selectedAccount = "100001284";

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - View transaction list by Account")
    public void viewTransactionByAccount() {
        //dang nhap tai khoan user, chon tai khoan va lay so tai khoan
        loginPage.login(Constants.USER_ID_1, Constants.USER_PASSWORD_1);
        accountNo = bankAccountsPage.getAccountNoByIndex(1);

        //dang nhap voi tai khoan admin
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.ADMIN_URL);
        loginAdminPage.loginAdmin(Constants.ADMIN_ID,Constants.ADMIN_PASSWORD);

        //Nhap data tim kiem
        homePage.openCustomerTransactionListPage();
        transactionCustomerPage.search(accountNo,dateFrominString,dateToinString);

        //kiem tra thong tin hien thi
        softAssert.assertEquals(transactionCustomerPage.getAccountNumbByRow(faker.number().numberBetween(1,2)),accountNo);
//        softAssert.assertTrue(transactionCustomerPage.isTransactionDateValid(dateFrominString,dateToinString,faker.number().numberBetween(0,2)));

        softAssert.assertAll();
    }
}

