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

public class TC06_ExternalTransfer {
    WebDriver driver;
    LoginPage loginPage;
    SoftAssert softAssert;
    BankAccountsPage bankAccountsPage;
    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmPage externalTransferConfirmPage;
    YopmailPage yopmailPage;
    String senderAcc;
    double transferAmount;
    String transferDesc;
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
        externalTransferPage = new ExternalTransferPage(driver);
        externalTransferConfirmPage = new ExternalTransferConfirmPage(driver);
        yopmailPage = new YopmailPage(driver);
        senderAcc = "100001284";
        faker = new Faker();
        transferAmount = faker.number().numberBetween(1,10)*1000;
        transferDesc = faker.name().fullName();


        driver.get(Constants.USER_URL);
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
        loginPage.login(Constants.USER_ID_1, Constants.USER_PASSWORD_1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openExternalTransferPage();
        externalTransferPage.selectAccountByAccNumber(senderAcc);
        softAssert.assertEquals(externalTransferPage.getSenderBalance(), senderBalance); //Kiem tra so du

        externalTransferPage.inputReceiverAccount(Constants.EXTERNAL_ACCOUNT_NUMBER);
        externalTransferPage.inputReceiverName(Constants.EXTERNAL_NAME);
        externalTransferPage.selectDongABank();
        externalTransferPage.selectDaNangBranch();
        externalTransferPage.inputTransferDescription(transferDesc);
        externalTransferPage.inputTransferAmount(transferAmount);
        externalTransferPage.clickTransferBtn();

        // Kiem tra thong tin chuyen khoan
        softAssert.assertEquals(externalTransferConfirmPage.getSenderAcc(),senderAcc);
        softAssert.assertEquals(externalTransferConfirmPage.getSenderBalance(),senderBalance);
        softAssert.assertEquals(externalTransferConfirmPage.getReceiverAcc(),Constants.EXTERNAL_ACCOUNT_NUMBER);
        softAssert.assertEquals(externalTransferConfirmPage.getTransferAmount(),transferAmount);
        softAssert.assertEquals(externalTransferConfirmPage.getTransferDesc(),transferDesc);
        softAssert.assertEquals(externalTransferConfirmPage.getReceiverName(),Constants.EXTERNAL_NAME);

        //Xac nhan va nhap OTP
        externalTransferConfirmPage.clickConfirmBtn();

        // Lay ma OTP tu Yopmail
        originalHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        OTP = yopmailPage.getOTPcodeByEmail(Constants.EMAIL_1);

        // Quay ve tab cu va nhap OTP
        driver.switchTo().window(originalHandle);

        externalTransferConfirmPage.inputOTP(OTP);

        externalTransferConfirmPage.clickTransferBtn();

        // Kiem tra message success
        softAssert.assertTrue(externalTransferConfirmPage.isSuccessMessageDisplayed());
        externalTransferConfirmPage.closeSuccessMessage();

        //Kiem tra so du tai khoan gui
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        softAssert.assertEquals(bankAccountsPage.getAccountBalance(), senderBalance - transferAmount - Constants.EXTERNAL_TRANSACTION_FEE);

        softAssert.assertAll();

    }
}
