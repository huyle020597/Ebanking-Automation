package example.UserTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TC08_VerifyFilterTransactionsFunction {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    BankAccountsPage bankAccountsPage;
    String selectedAccount;
    TransactionsPage transactionsPage;
    Faker faker;
    LocalDate randomDate1;
    LocalDate randomDate2;
    DateTimeFormatter formatter;


    Date startDate; //Dùng biến này cho hàm faker lấy randomeDate
    LocalDate startLocalDate;
    LocalDate endLocalDate;
    String startDateinString;
    String endDateinString;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        bankAccountsPage = new BankAccountsPage(driver);
        transactionsPage = new TransactionsPage(driver);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        faker = new Faker();
        //Lấy ngày start random
        randomDate1 = LocalDate.of(2024,12,12);
        randomDate2 = LocalDate.of(2024,12,15);
        startDate = faker.date().between(Date.from(randomDate1.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(randomDate2.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        //Lấy ngày end random
        startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        endLocalDate = startLocalDate.plusDays(5);

        //Chuyển về String để input
        startDateinString = formatter.format(startLocalDate);
        endDateinString = formatter.format(endLocalDate);

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC08() {
        loginPage.login(Constants.USER_1);
        bankAccountsPage.openTransactionsPage();

        transactionsPage.searchTransaction(Constants.USER_1.getBankAccount(),startDateinString,endDateinString);

        //Verify Account Number va Transaction Date
        softAssert.assertTrue(transactionsPage.isTransactionAccountsValid(Constants.USER_1.getBankAccount()),"Transaction Account invalid");
        softAssert.assertTrue(transactionsPage.isTransactionsDateBetween(startDateinString,endDateinString),"Transaction Date invalid");

        softAssert.assertAll();
    }

}
