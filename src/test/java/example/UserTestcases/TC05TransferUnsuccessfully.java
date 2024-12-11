package example.UserTestcases;

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

public class TC05TransferUnsuccessfully {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    OpenBankAccountPage openBankAccountPage;
    BankAccountsPage bankAccountsPage;
    InternalTransferPage internalTransferPage;
    InternalTransferConfirmPage internalTransferConfirmPage;
    YopmailPage yopmailPage;
    String userId1;
    String password1;
    String userId2;
    String password2;
    String receiverAcc;
    String senderAcc;
    String transferDesc;
    double transferAmount;
    double transactionFee;
    double senderBalance;
    String OTP;
    String originalHandle;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        openBankAccountPage = new OpenBankAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        internalTransferPage = new InternalTransferPage(driver);
        internalTransferConfirmPage = new InternalTransferConfirmPage(driver);
        yopmailPage = new YopmailPage(driver);
        userId1 = "huyle020597";
        password1 = "Maddie123@";
        userId2 = "huyle0205971";
        password2 = "Maddie123@";
        senderAcc = "100001283";
        receiverAcc = "100001298";
        transferAmount = 20000.0;
        transactionFee = Constants.INTERNAL_TRANSACTION_FEE;
        transferDesc = "Hello";

        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC05() throws InterruptedException {
        // Login voi tai khoan gui và kiem tra so du
        loginPage.login(userId1, password1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openTransferPage();
        internalTransferPage.selectAccountByAccNumber(senderAcc);

        softAssert.assertEquals(internalTransferPage.getAccBalance(), senderBalance);

        internalTransferPage.inputReceiverAccount(receiverAcc + "1");
        softAssert.assertTrue(internalTransferPage.isReceiverNameEmpty()); //Check name empty

        internalTransferPage.inputMoneyAmount(transferAmount);

        internalTransferPage.inputTransferDescription(transferDesc);

        internalTransferPage.clickConfirmBtn();

        softAssert.assertTrue(internalTransferPage.isInvalidAccMessageDisplayed()); //Check loi~ tai khoan khong hop le

        internalTransferPage.inputReceiverAccount(receiverAcc);

        internalTransferPage.inputMoneyAmount(2000000000);

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
        driver.get("https://yopmail.com");
        Thread.sleep(3000);
        yopmailPage.inputEmailAddress(userId1);
        OTP = yopmailPage.getOTPcode();


        // Quay ve tab cu
        driver.switchTo().window(originalHandle);
        // Nhap sai OTP
        internalTransferConfirmPage.inputOTP(OTP+"A");
        internalTransferConfirmPage.clickTransferBtn();
        internalTransferConfirmPage.isWrongOTPMessageDisplayed();
        // Nhap dung OTP
        internalTransferConfirmPage.inputOTP(OTP);
        internalTransferConfirmPage.clickTransferBtn();

        // Kiem tra message success
        softAssert.assertTrue(internalTransferConfirmPage.isTransferSuccessMessageDisplayed());
        internalTransferConfirmPage.closeTransferSuccessMessage();

        //Kiem tra so du tai khoan gui
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - transactionFee);

        softAssert.assertAll();
    }
}
