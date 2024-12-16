package example.UserTestcases;

import com.github.javafaker.Faker;
import modal.Constants;
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
    String receiverAcc;
    String senderAcc;
    double transferAmount;
    String OTP;
    String transferDescription;
    double receiverBalance;
    double senderBalance;
    Faker faker;


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
        senderAcc = "100001284";
        receiverAcc = "100001298";
        faker = new Faker();
        transferDescription = faker.name().fullName();

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC04() throws InterruptedException {
        // Login tai khoan nhan va kiem tra so du
        loginPage.login(Constants.USER_ID_2, Constants.PASSWORD_2);
        bankAccountsPage.viewDetailsByAccNumber(receiverAcc);
        receiverBalance = bankAccountsPage.getAccountBalance();


        // Login voi tai khoan gui và kiem tra so du
        bankAccountsPage.LogOut();
        loginPage.login(Constants.USER_ID_1, Constants.PASSWORD_1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openTransferPage();

        internalTransferPage.inputTransferInfo(senderAcc,receiverAcc,transferAmount,transferDescription);

        softAssert.assertEquals(internalTransferPage.getAccBalance(), senderBalance);//Kiem tra so du


        internalTransferPage.clickConfirmBtn();

        // Kiem tra thong tin da nhap
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), receiverAcc);
        softAssert.assertEquals(internalTransferConfirmPage.getAvailableBalance(), senderBalance);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferAmount(), transferAmount);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferDescription(), transferDescription);
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), receiverAcc);

        internalTransferConfirmPage.clickConfirmBtn();

        // Lay ma OTP tu Yopmail
        String originalHandle = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.TAB);

        OTP = yopmailPage.getOTPcodeByEmail(Constants.EMAIL_1);

        // Quay ve tab cu va nhap OTP
        driver.switchTo().window(originalHandle);

        internalTransferConfirmPage.inputOTP(OTP);

        internalTransferConfirmPage.clickTransferBtn();

        // Kiem tra message success
        softAssert.assertTrue(internalTransferConfirmPage.isTransferSuccessMessageDisplayed());
        internalTransferConfirmPage.closeTransferSuccessMessage();

        //Kiem tra so du tai khoan gui
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - Constants.INTERNAL_TRANSACTION_FEE);

        //Kiem tra so du tai khoan nhan
        bankAccountsPage.LogOut();

        loginPage.login(Constants.USER_ID_2, Constants.PASSWORD_2);
        bankAccountsPage.viewDetailsByAccNumber(receiverAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), receiverBalance + transferAmount);

        softAssert.assertAll();
    }
}
