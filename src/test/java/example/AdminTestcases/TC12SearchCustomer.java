package example.AdminTestcases;

import page.AdminPages.CustomerList;
import page.AdminPages.HomePage;
import page.AdminPages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class TC12SearchCustomer {
    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    CustomerList customerList;
    SoftAssert softAssert;
    String customerID; String phoneNumber;
    String dateFrom;     String dateTo;
    String adminId;     String password;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        homePage = new HomePage(driver);
        customerList = new CustomerList(driver);
        customerID = "ngochien123"; phoneNumber = "0799453330";
        dateFrom = "20112024"; dateTo = "20122024";
        adminId = "1"; password = "admin";


        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - Search customer by multiple fields")
    public void searchCustomer() {
        loginPage.loginAdmin(adminId,password);

        //Nhap data tim kiem
        homePage.openCustomerListPage();
        customerList.enterCustomerID(customerID);
        customerList.enterPhoneNumber(phoneNumber);

        //kiem tra ket qua hien thi
        softAssert.assertEquals(phoneNumber, customerList.phoneNumberSearchReturn());


        softAssert.assertAll();

    }
}
