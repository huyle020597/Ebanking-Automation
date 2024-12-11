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

public class TC06ExternalTransfer {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    OpenBankAccountPage openBankAccountPage;
    BankAccountsPage bankAccountsPage;
    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmPage externalTransferConfirmPage;
    YopmailPage yopmailPage;
    String userId1;
    String password1;
    String receiverName;
    String receiverAcc;
    String senderAcc;
    double transferAmount;
    String transferDesc;
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
        externalTransferPage = new ExternalTransferPage(driver);
        externalTransferConfirmPage = new ExternalTransferConfirmPage(driver);
        yopmailPage = new YopmailPage(driver);
        userId1 = "huyle020597";
        password1 = "Maddie123@";
        receiverName = "Nguyen Van A";
        senderAcc = "100001283";
        receiverAcc = "10001111";
        transferAmount = 20000.0;
        transferDesc = "Hello";
        transactionFee = Constants.EXTERNAL_TRANSACTION_FEE;

        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC06 () throws InterruptedException {
        // Login voi tai khoan gui và kiem tra so du
        loginPage.login(userId1, password1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openExternalTransferPage();
        externalTransferPage.selectAccountByAccNumber(senderAcc);
        Thread.sleep(500);
        softAssert.assertEquals(externalTransferPage.getSenderBalance(), senderBalance); //Kiem tra so du

        externalTransferPage.inputReceiverAccount(receiverAcc);
        externalTransferPage.inputReceiverName(receiverName);
        externalTransferPage.selectDongABank();
        Thread.sleep(500);
        externalTransferPage.selectDaNangBranch();
        externalTransferPage.inputTransferDescription(transferDesc);
        externalTransferPage.inputTransferAmount(transferAmount);
        externalTransferPage.clickTransferBtn();

        // Kiem tra thong tin chuyen khoan
        softAssert.assertEquals(externalTransferConfirmPage.getSenderAcc(),senderAcc);
        softAssert.assertEquals(externalTransferConfirmPage.getSenderBalance(),senderBalance);
        softAssert.assertEquals(externalTransferConfirmPage.getReceiverAcc(),receiverAcc);
        softAssert.assertEquals(externalTransferConfirmPage.getTransferAmount(),transferAmount);
        softAssert.assertEquals(externalTransferConfirmPage.getTransferDesc(),transferDesc);
        softAssert.assertEquals(externalTransferConfirmPage.getReceiverName(),receiverName);

        //Xac nhan va nhap OTP
        externalTransferConfirmPage.clickConfirmBtn();

        // Lay ma OTP tu Yopmail
        originalHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://yopmail.com");
        Thread.sleep(3000);
        yopmailPage.inputEmailAddress(userId1);
        OTP = yopmailPage.getOTPcode();

        // Quay ve tab cu va nhap OTP
        driver.switchTo().window(originalHandle);

        externalTransferConfirmPage.inputOTP(OTP);

        externalTransferConfirmPage.clickTransferBtn();

        // Kiem tra message success
        softAssert.assertTrue(externalTransferConfirmPage.isSuccessMessageDisplayed());
        externalTransferConfirmPage.closeSuccessMessage();

        //Kiem tra so du tai khoan gui
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - transactionFee);

        softAssert.assertAll();

    }
}
