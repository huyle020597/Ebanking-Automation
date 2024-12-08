package example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.*;

import java.time.Duration;

public class TC05TransferUnsuccessfully {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    OpenAccountPage openAccountPage;
    BankAccountsPage bankAccountsPage;
    TransferPage transferPage;
    TransferConfirmationPage transferConfirmationPage;
    YopmailPage yopmailPage;
    String userId1; String password1;
    String userId2; String password2;
    String receiverAcc; String senderAcc;
    double transferAmount;
    double transactionFee;

    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        openAccountPage = new OpenAccountPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        transferPage = new TransferPage(driver);
        transferConfirmationPage = new TransferConfirmationPage(driver);
        yopmailPage = new YopmailPage(driver);
        userId1 = "huyle020597"; password1 = "Maddie123@";
        userId2 = "huyle0205971"; password2 = "Maddie123@";
        senderAcc = "100001283" ; receiverAcc = "100001298";
        transferAmount = 20000.0;
        transactionFee = 1100;

        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void TC05 () throws InterruptedException {
        // Login voi tai khoan gui và kiem tra so du
        loginPage.login(userId1, password1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        double senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openTransferPage();
        transferPage.clickAccDropdown();
        transferPage.selectAccountByAccNumber(senderAcc);

        Thread.sleep(500);
        softAssert.assertEquals(transferPage.getAccBalance(),senderBalance);

        transferPage.inputReceiverAccount(receiverAcc+"1");
        softAssert.assertTrue(transferPage.isReceiverNameEmpty()); //Check name empty

        transferPage.inputMoneyAmount(transferAmount);

        transferPage.inputTransferDescription("Hello");

        transferPage.clickConfirmBtn();

        softAssert.assertTrue(transferPage.isInvalidAccMessageDisplayed()); //Check loi~ tai khoan khong hop le

        transferPage.inputReceiverAccount(receiverAcc);

        transferPage.inputMoneyAmount(2000000000);

        transferPage.clickConfirmBtn();

        softAssert.assertTrue(transferPage.isInsufficientMessageDisplayed()); //Check loi~ so tien vuot muc

        transferPage.inputMoneyAmount(transferAmount);

        transferPage.clickConfirmBtn();


        // Kiem tra thong tin da nhap
        softAssert.assertEquals(transferConfirmationPage.getReceiverAcc(),receiverAcc);
        softAssert.assertEquals(transferConfirmationPage.getAvailableBalance(),senderBalance);
        softAssert.assertEquals(transferConfirmationPage.getTransferAmount(),transferAmount);
        softAssert.assertEquals(transferConfirmationPage.getTransferDescription(),"Hello");
        softAssert.assertEquals(transferConfirmationPage.getReceiverAcc(),receiverAcc);

        transferConfirmationPage.clickConfirmBtn();
        Thread.sleep(3000);

        //Nhap sai OTP
        transferConfirmationPage.inputOTP("ABCDEFGH");
        transferConfirmationPage.clickTransferBtn();
        transferConfirmationPage.isWrongOTPMessageDisplayed();

        // Lay ma OTP tu Yopmail
        String originalHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://yopmail.com");
        yopmailPage.inputEmailAddress(userId1);
        String OTP = yopmailPage.getOTPcode();

        // Quay ve tab cu va nhap OTP
        driver.switchTo().window(originalHandle);
        transferConfirmationPage.inputOTP(OTP);
        transferConfirmationPage.clickTransferBtn();

        // Kiem tra message success
        softAssert.assertTrue(transferConfirmationPage.isTransferSuccessMessageDisplayed());
        transferConfirmationPage.closeTransferSuccessMessage();

        //Kiem tra so du tai khoan gui
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(),senderBalance-transferAmount-transactionFee);

        softAssert.assertAll();
    }
}
