package example.UserTestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.*;

import java.time.Duration;

public class TC04InternalTransfer {
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
    double transferAmount;
    double transactionFee;

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
    public void TC04() throws InterruptedException {
        // Login tai khoan nhan va kiem tra so du
        loginPage.login(userId2, password2);
        bankAccountsPage.viewDetailsByAccNumber(receiverAcc);
        double receiverBalance = bankAccountsPage.getAccountBalance();


        // Login voi tai khoan gui và kiem tra so du
        bankAccountsPage.clickLogoutBtn();
        loginPage.login(userId1, password1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        double senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openTransferPage();
        internalTransferPage.clickAccDropdown();
        internalTransferPage.selectAccountByAccNumber(senderAcc);

        softAssert.assertEquals(internalTransferPage.getAccBalance(), senderBalance); //Kiem tra so du

        internalTransferPage.inputReceiverAccount(receiverAcc);

        internalTransferPage.inputMoneyAmount(transferAmount);

        internalTransferPage.inputTransferDescription("Hello");

        internalTransferPage.clickConfirmBtn();

        // Kiem tra thong tin da nhap
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), receiverAcc);
        softAssert.assertEquals(internalTransferConfirmPage.getAvailableBalance(), senderBalance);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferAmount(), transferAmount);
        softAssert.assertEquals(internalTransferConfirmPage.getTransferDescription(), "Hello");
        softAssert.assertEquals(internalTransferConfirmPage.getReceiverAcc(), receiverAcc);

        internalTransferConfirmPage.clickConfirmBtn();
        Thread.sleep(3000);

        String originalHandle = driver.getWindowHandle();

        // Lay ma OTP tu Yopmail
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://yopmail.com");
        yopmailPage.inputEmailAddress(userId1);
        String OTP = yopmailPage.getOTPcode();

        // Quay ve tab cu va nhap OTP
        driver.switchTo().window(originalHandle);

        internalTransferConfirmPage.inputOTP(OTP);

        internalTransferConfirmPage.clickTransferBtn();

        // Kiem tra message success, so du cua 2 tai khoan
        //Kiem tra message
        softAssert.assertTrue(internalTransferConfirmPage.isTransferSuccessMessageDisplayed());
        internalTransferConfirmPage.closeTransferSuccessMessage();

        //Kiem tra so du tai khoan gui
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - transactionFee);

        //Kiem tra so du tai khoan nhan
        menuBar.clickLogoutBtn();

        loginPage.login(userId2, password2);
        bankAccountsPage.viewDetailsByAccNumber(receiverAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), receiverBalance + transferAmount);

        softAssert.assertAll();
    }
}
