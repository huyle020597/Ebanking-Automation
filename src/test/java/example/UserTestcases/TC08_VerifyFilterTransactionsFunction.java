package example.UserTestcases;

import com.github.javafaker.Faker;
import modal.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TC08_VerifyFilterTransactionsFunction {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    BankAccountsPage bankAccountsPage;
    String selectedAccount;
    TransactionsPage transactionsPage;
    Faker faker;
    SimpleDateFormat formatter;
    Date start1;
    Date end1;

    Date start2;
    Date end2;

    Date startDate;
    Date endDate;
    String startDateinString;
    String endDateinString;

    Actions actions;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        bankAccountsPage = new BankAccountsPage(driver);
        transactionsPage = new TransactionsPage(driver);
        selectedAccount = "100001284";
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        faker = new Faker();
        actions = new Actions(driver);

        start1 = Date.from(LocalDate.of(2024,12,1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        end1 = Date.from(LocalDate.of(2024,12,15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        startDate = faker.date().between(start1,end1);

        start2 = Date.from(LocalDate.of(2024,12,16).atStartOfDay(ZoneId.systemDefault()).toInstant());
        end2 = Date.from(LocalDate.of(2024,12,30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        endDate = faker.date().between(start2,end2);
// random 1 ngay, + them vai ngay

        startDateinString = formatter.format(startDate);
        endDateinString = formatter.format(endDate);

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

//    @AfterMethod
//    public void cleanUp() {
//        driver.quit();
//    }

    @Test
    public void TC08() {
        loginPage.login(Constants.USER_ID_1,Constants.PASSWORD_1);
        bankAccountsPage.openTransactionsPage();

        transactionsPage.searchTransaction(selectedAccount,startDateinString,endDateinString);
        transactionsPage.selectAccountByAccNumber(selectedAccount);
        transactionsPage.inputFromDate(startDateinString);
        transactionsPage.inputToDate(endDateinString);
//        actions.moveByOffset(50,50).click().perform();
        transactionsPage.clickSearchBtn();

        //Kiem tra Account Number va Transaction Date
        softAssert.assertEquals(transactionsPage.getAccountNumbByRow(faker.number().numberBetween(1,5)),selectedAccount);
        softAssert.assertTrue(transactionsPage.isTransactionDateValid(startDateinString,endDateinString,faker.number().numberBetween(1,5)));


        softAssert.assertAll();
    }

}
