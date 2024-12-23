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
    String selectedAccount;
    boolean isDateValid;


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
        randomDate2 = LocalDate.of(2024, 12, 15);
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
        loginAdminPage.loginAdmin(Constants.ADMIN_ID, Constants.ADMIN_PASSWORD);

        //Nhap data tim kiem
        homePage.openCustomerTransactionListPage();
        transactionCustomerPage.search(accountNo, dateFrominString, dateToinString);

        // Sử dụng iterateTransactionRows để kiểm tra thông tin hiển thị
//        transactionCustomerPage.iterateTransactionRows((rowIndex, senderAccount, transactionDate) -> {
//            // Kiểm tra tài khoản người gửi khớp với số tài khoản đã chọn
//            softAssert.assertEquals(senderAccount, accountNo, "Sender account does not match at row " + rowIndex);
//
//            // Kiểm tra ngày giao dịch nằm trong khoảng ngày đã chọn
//            isDateValid = transactionCustomerPage.isTransactionDateValid(dateFrominString, dateToinString);
//            softAssert.assertTrue(isDateValid, "Transaction date is not valid at row " + rowIndex);
//        });

            //kiem tra thong tin hien thi
//            softAssert.assertTrue(senderAccount.equals(accountNo) || receiverAccount.equals(accountNo),
//             "Dữ liệu không khớp với số tài khoản!");
//            softAssert.assertEquals(senderAccount, accountNo, "Sender account does not match!");
//            //softAssert.assertEquals(transactionCustomerPage.getAccountNumbByRow(faker.number().numberBetween(1,5)),accountNo);
//            softAssert.assertTrue(transactionCustomerPage.isTransactionDateValid(dateFrominString, dateToinString));

            softAssert.assertAll();
        }
    }

