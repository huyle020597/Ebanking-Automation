package example.AdminTestcases;

import org.openqa.selenium.WebElement;
import page.AdminPages.HomePage;
import page.AdminPages.LoginPage;
import page.AdminPages.TransactionCustomerPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;

import java.time.Duration;


public class TC11 {
    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    TransactionCustomerPage transactionCustomerPage;
    SoftAssert softAssert;
    String accountNumber;
    String dateFrom;     String dateTo;
    String adminId;     String adminPassword;
    boolean transactionFound;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        homePage = new HomePage(driver);
        transactionCustomerPage = new TransactionCustomerPage(driver);
        accountNumber = "100001283";
        dateFrom = "20112024"; dateTo = "20122024";
        adminId = "1"; adminPassword = "admin";
        transactionFound = false;


        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));


    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - View transaction list by Account")
    public void viewTransactionByAccount() {
        loginPage.loginAdmin(adminId, adminPassword);

        //Nhap data tim kiem
        homePage.openCustomerTransactionListPage();
        transactionCustomerPage.inputAccountNumber(accountNumber);
        transactionCustomerPage.inputDateFrom(dateFrom);
        transactionCustomerPage.inputDateTo(dateTo);
        transactionCustomerPage.clickSearchBtn();

        //kiem tra thong tin hien thi
        //if (!accountRows.isEmpty()) {
            // Chọn tài khoản đầu tiên (có thể thay đổi nếu cần)
      //      WebElement firstAccountRow = accountRows.get(1);

            softAssert.assertAll();
    }


}

