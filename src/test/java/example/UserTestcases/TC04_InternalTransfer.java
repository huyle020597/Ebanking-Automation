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

public class TC04_InternalTransfer {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    OpenBankAccountPage openBankAccountPage;
    BankAccountsPage bankAccountsPage;
    InternalTransferPage internalTransferPage;
    InternalTransferConfirmPage internalTransferConfirmPage;
    YopmailPage yopmailPage;
    double transferAmount;
    String OTP;
    String transferDescription;
    double receiverBalance;
    double senderBalance;
    Faker faker;
    String originalHandle;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        openBankAccountPage = new OpenBankAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        internalTransferPage = new InternalTransferPage(driver);
        internalTransferConfirmPage = new InternalTransferConfirmPage(driver);
        yopmailPage = new YopmailPage(driver);
        faker = new Faker();
        transferDescription = faker.name().fullName();
        transferAmount = faker.number().numberBetween(1,10)*1000;

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC04() throws InterruptedException {
        // Login with Receiver Account and check Balance
        loginPage.login(Constants.USER_ID_2, Constants.USER_PASSWORD_2);
        bankAccountsPage.viewDetailsByAccNumber(Constants.USER_2.getBankAccount());
        receiverBalance = bankAccountsPage.getAccountBalance();


        // Login with Sender Account and check Balance
        bankAccountsPage.LogOut();
        loginPage.login(Constants.USER_ID_1, Constants.USER_PASSWORD_1);
        bankAccountsPage.viewDetailsByAccNumber(Constants.USER_1.getBankAccount());
        senderBalance = bankAccountsPage.getAccountBalance();

        // Transfer money
        bankAccountsPage.openTransferPage();

        internalTransferPage.inputTransferInfo(Constants.USER_1.getBankAccount(),Constants.USER_2.getBankAccount()
                , transferAmount,transferDescription);

        softAssert.assertEquals(internalTransferPage.getAccBalance(), senderBalance);


        internalTransferPage.clickConfirmBtn();

        // Verify the data input
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), Constants.USER_2.getBankAccount());
        softAssert.assertEquals(internalTransferConfirmPage.getAvailableBalance(), senderBalance);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferAmount(), transferAmount);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferDescription(), transferDescription);
        softAssert.assertEquals(internalTransferConfirmPage.getSenderAcc(), Constants.USER_1.getBankAccount());

        internalTransferConfirmPage.clickConfirmBtn();

        // Get OTP code
        originalHandle = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.TAB);

        driver.get(Constants.YOPMAIL_URL);

        OTP = yopmailPage.getOTPcodeByEmail(Constants.USER_1.getEmailAddress());

        // Return to original tab and input OTP
        driver.switchTo().window(originalHandle);

        internalTransferConfirmPage.inputOTP(OTP);

        internalTransferConfirmPage.clickTransferBtn();

        // Verify successful message
        softAssert.assertTrue(internalTransferConfirmPage.isTransferSuccessMessageDisplayed());
        internalTransferConfirmPage.closeTransferSuccessMessage();

        // Verify sender account balance
        bankAccountsPage.viewDetailsByAccNumber(Constants.USER_1.getBankAccount());
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - Constants.INTERNAL_TRANSACTION_FEE);

        // Verify receiver account balance
        bankAccountsPage.LogOut();

        loginPage.login(Constants.USER_ID_2, Constants.USER_PASSWORD_2);
        bankAccountsPage.viewDetailsByAccNumber(Constants.USER_2.getBankAccount());
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), receiverBalance + transferAmount);

        softAssert.assertAll();
    }
}
