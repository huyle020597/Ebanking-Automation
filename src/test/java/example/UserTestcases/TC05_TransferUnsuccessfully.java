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

public class TC05_TransferUnsuccessfully {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    BankAccountsPage bankAccountsPage;
    InternalTransferPage internalTransferPage;
    InternalTransferConfirmPage internalTransferConfirmPage;
    YopmailPage yopmailPage;
    String transferDesc;
    double transferAmount;
    double senderBalance;
    String OTP;
    String originalHandle;
    Faker faker;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        bankAccountsPage = new BankAccountsPage(driver);
        internalTransferPage = new InternalTransferPage(driver);
        internalTransferConfirmPage = new InternalTransferConfirmPage(driver);
        yopmailPage = new YopmailPage(driver);
        faker = new Faker();
        transferDesc = faker.name().fullName();
        transferAmount = faker.number().numberBetween(1,10)*1000;

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC05() throws InterruptedException {
        // Login with Sender Account and check Balance
        loginPage.login(Constants.USER_1);
        bankAccountsPage.viewDetailsByAccNumber(Constants.USER_1.getBankAccount());
        senderBalance = bankAccountsPage.getAccountBalance();

        // Transfer Money
        bankAccountsPage.openTransferPage();
        internalTransferPage.selectAccountByAccNumber(Constants.USER_1.getBankAccount());

        internalTransferPage.inputReceiverAccount(Constants.USER_2.getBankAccount() + "1");


        internalTransferPage.inputTransferAmount(transferAmount);

        internalTransferPage.inputTransferDescription(transferDesc);

        softAssert.assertEquals(internalTransferPage.getAccBalance(), senderBalance); // Check account balance
        softAssert.assertTrue(internalTransferPage.isReceiverNameEmpty()); //Check name empty

        internalTransferPage.clickConfirmBtn();

        softAssert.assertTrue(internalTransferPage.isInvalidAccMessageDisplayed()); //Check error invalid bank account

        internalTransferPage.inputReceiverAccount(Constants.USER_2.getBankAccount());

        internalTransferPage.inputTransferAmount(senderBalance+1);

        internalTransferPage.clickConfirmBtn();

        softAssert.assertTrue(internalTransferPage.isInsufficientMessageDisplayed()); //Check error Insufficient Fund

        internalTransferPage.inputTransferAmount(transferAmount);

        internalTransferPage.clickConfirmBtn();


        // Verify data
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), Constants.USER_2.getBankAccount());
        softAssert.assertEquals(internalTransferConfirmPage.getAvailableBalance(), senderBalance);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferAmount(), transferAmount);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferDescription(), transferDesc);
        softAssert.assertEquals(internalTransferConfirmPage.getSenderAcc(), Constants.USER_1.getBankAccount());

        internalTransferConfirmPage.clickConfirmBtn();


        // Get OTP
        originalHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.YOPMAIL_URL);
        OTP = yopmailPage.getOTPcodeByEmail(Constants.USER_1.getEmailAddress());

        driver.switchTo().window(originalHandle);

        // Input wrong OTP
        internalTransferConfirmPage.inputOTP(OTP+"A");
        internalTransferConfirmPage.clickTransferBtn();
        softAssert.assertTrue(internalTransferConfirmPage.isWrongOTPMessageDisplayed());

        // Input correct OTP
        internalTransferConfirmPage.inputOTP(OTP);
        internalTransferConfirmPage.clickTransferBtn();

        // Verify success message
        softAssert.assertTrue(internalTransferConfirmPage.isTransferSuccessMessageDisplayed());
        internalTransferConfirmPage.closeTransferSuccessMessage();

        // Verify Sender account balance
        bankAccountsPage.viewDetailsByAccNumber(Constants.USER_1.getBankAccount());
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - Constants.INTERNAL_TRANSACTION_FEE);

        softAssert.assertAll();
    }
}
