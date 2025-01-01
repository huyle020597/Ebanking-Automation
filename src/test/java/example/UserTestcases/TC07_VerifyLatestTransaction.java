package example.UserTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TC07_VerifyLatestTransaction {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    BankAccountsPage bankAccountsPage;
    InternalTransferPage internalTransferPage;
    InternalTransferConfirmPage internalTransferConfirmPage;
    YopmailPage yopmailPage;;
    String receiverAcc;
    String senderAcc;
    double transferAmount;
    String transferDesc;
    String OTP;
    String originalHandle;
    LocalDate localDate;
    Faker faker;
    LocalDateTime now;
    DateTimeFormatter formatter;
    String transferTime;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        bankAccountsPage = new BankAccountsPage(driver);
        internalTransferPage = new InternalTransferPage(driver);
        internalTransferConfirmPage = new InternalTransferConfirmPage(driver);
        yopmailPage = new YopmailPage(driver);
        senderAcc = "100001284";
        receiverAcc = "100001298";
        faker=new Faker();
        transferAmount = faker.number().numberBetween(1,20)*1000;
        transferDesc = faker.name().fullName();
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC07() throws InterruptedException {

        // Login with sender account and check balance
        loginPage.login(Constants.USER_1);

        // Transfer money
        bankAccountsPage.openTransferPage();

        internalTransferPage.inputTransferInfo(senderAcc,receiverAcc,transferAmount,transferDesc);

        internalTransferPage.clickConfirmBtn();

        internalTransferConfirmPage.clickConfirmBtn();

        // Get OTP
        originalHandle = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.YOPMAIL_URL);

        OTP = yopmailPage.getOTPcodeByEmail(Constants.USER_1.getEmailAddress());

        // Input OTP
        driver.switchTo().window(originalHandle);

        internalTransferConfirmPage.inputOTP(OTP);

        internalTransferConfirmPage.clickTransferBtn();

        now = LocalDateTime.now();
        transferTime = now.format(formatter);

        internalTransferConfirmPage.closeTransferSuccessMessage();

        // Verify the information of sender latest transaction
        softAssert.assertEquals(bankAccountsPage.getTransactionDateByIndex(1),transferTime);
        softAssert.assertEquals(bankAccountsPage.getTransactionAccNumberByIndex(1),senderAcc);
        softAssert.assertTrue(bankAccountsPage.getTransactionAmountByIndex(1) <0);
        softAssert.assertEquals(bankAccountsPage.getActualTransactionAmountByIndex(1),transferAmount);


        // Verify the information of receiver latest transaction
        bankAccountsPage.LogOut();

        loginPage.login(Constants.USER_2);

        softAssert.assertEquals(bankAccountsPage.getTransactionDateByIndex(1),transferTime);
        softAssert.assertEquals(bankAccountsPage.getTransactionAccNumberByIndex(1),receiverAcc);
        softAssert.assertTrue(bankAccountsPage.getTransactionAmountByIndex(1) >0);
        softAssert.assertEquals(bankAccountsPage.getActualTransactionAmountByIndex(1),transferAmount);

        softAssert.assertAll();
    }
}
