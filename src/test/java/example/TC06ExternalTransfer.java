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
    double transactionFee;

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
    public void TC06 () {
        // Login voi tai khoan gui và kiem tra so du
        loginPage.login(userId1, password1);
        bankAccountsPage.viewDetailsByAccNumber(senderAcc);
        double senderBalance = bankAccountsPage.getAccountBalance();

        // Thuc hien chuyen tien
        bankAccountsPage.openTransferPage();
        externalTransferPage.clickAccountDropdown();
        externalTransferPage.selectAccountByAccNumber(senderAcc);

        softAssert.assertEquals(externalTransferPage.getSenderBalance(), senderBalance); //Kiem tra so du


    }
}
