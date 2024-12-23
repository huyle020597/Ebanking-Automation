package example.UserTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
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
    String receiverAcc;
    String senderAcc;
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
        senderAcc = "100001284";
        receiverAcc = "100001298";
        faker = new Faker();
        transferDesc = faker.name().fullName();
        transferAmount = faker.number().numberBetween(1,10)*1000;

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

//    @AfterMethod
//    public void cleanUp() {
//        driver.quit();
//    }

    @Test
    public void TC05() throws InterruptedException {
        // Login voi tai khoan gui và kiem tra so du
        loginPage.login(Constants.USER_ID_1, Constants.USER_PASSWORD_1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openTransferPage();
        internalTransferPage.selectAccountByAccNumber(senderAcc);
        Thread.sleep(500); //check hàm wait

        softAssert.assertEquals(internalTransferPage.getAccBalance(), senderBalance); //Check balance

        internalTransferPage.inputReceiverAccount(receiverAcc + "1");
        softAssert.assertTrue(internalTransferPage.isReceiverNameEmpty()); //Check name empty

        internalTransferPage.inputMoneyAmount(transferAmount);

        internalTransferPage.inputTransferDescription(transferDesc);

        internalTransferPage.clickConfirmBtn();

        softAssert.assertTrue(internalTransferPage.isInvalidAccMessageDisplayed()); //Check loi~ tai khoan khong hop le

        internalTransferPage.inputReceiverAccount(receiverAcc);

        internalTransferPage.inputMoneyAmount(senderBalance+1);

        internalTransferPage.clickConfirmBtn();

        softAssert.assertTrue(internalTransferPage.isInsufficientMessageDisplayed()); //Check loi~ so tien vuot muc

        internalTransferPage.inputMoneyAmount(transferAmount);

        internalTransferPage.clickConfirmBtn();


        // Kiem tra thong tin da nhap
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), receiverAcc);
        softAssert.assertEquals(internalTransferConfirmPage.getAvailableBalance(), senderBalance);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferAmount(), transferAmount);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferDescription(), transferDesc);
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), receiverAcc);

        internalTransferConfirmPage.clickConfirmBtn();


        // Lay ma OTP tu Yopmail
        originalHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.YOPMAIL_URL);
        OTP = yopmailPage.getOTPcodeByEmail(Constants.EMAIL_1);

        // Quay ve tab cu
        driver.switchTo().window(originalHandle);

        // Nhap sai OTP
        internalTransferConfirmPage.inputOTP(OTP+"A");
        internalTransferConfirmPage.clickTransferBtn();
        softAssert.assertTrue(internalTransferConfirmPage.isWrongOTPMessageDisplayed());

        // Nhap dung OTP
        internalTransferConfirmPage.inputOTP(OTP);
        internalTransferConfirmPage.clickTransferBtn();

        // Kiem tra message success
        softAssert.assertTrue(internalTransferConfirmPage.isTransferSuccessMessageDisplayed());
        internalTransferConfirmPage.closeTransferSuccessMessage();

        //Kiem tra so du tai khoan gui
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - Constants.INTERNAL_TRANSACTION_FEE);

        softAssert.assertAll();
    }
}
