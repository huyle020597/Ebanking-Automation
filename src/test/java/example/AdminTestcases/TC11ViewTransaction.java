package example.AdminTestcases;

import com.github.javafaker.Faker;
import modal.Constants;
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
        endDate = faker.date().between(dateFrom, dateTo);

        dateFrominString = formatter.format(startDate);
        dateToinString = formatter.format(endDate);


        driver.get(Constants.USER_URL);
        driver.get(Constants.ADMIN_URL);
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
       // loginPage.login(Constants.userId1, Constants.password1);
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml");
        loginPage.login("huyle020597","Maddie123@");
        accountNo = bankAccountsPage.getAccountNoByIndex(1);

        //dang nhap voi tai khoan admin
        driver.switchTo().newWindow(WindowType.TAB);
        //loginAdminPage.loginAdmin(Constants.adminId,Constants.adminPassword);
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
        loginAdminPage.loginAdmin("1","admin");

        //Nhap data tim kiem
        homePage.openCustomerTransactionListPage();
        transactionCustomerPage.inputAccountNumber(accountNo);
        transactionCustomerPage.inputDateFrom(dateFrominString);
        transactionCustomerPage.inputDateTo(dateToinString);
        transactionCustomerPage.clickSearchBtn();

        //kiem tra thong tin hien thi
        softAssert.assertEquals(transactionCustomerPage.getAccountNumbByRow(faker.number().numberBetween(1,2)),accountNo);
        softAssert.assertTrue(transactionCustomerPage.isTransactionDateValid(dateFrominString,dateToinString,faker.number().numberBetween(1,3)));

        softAssert.assertAll();
    }
}

