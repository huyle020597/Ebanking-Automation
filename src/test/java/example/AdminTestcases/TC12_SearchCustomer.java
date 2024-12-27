package example.AdminTestcases;

import model.Constants;
import org.testng.annotations.AfterMethod;
import page.AdminPages.CustomerListPage;
import page.AdminPages.HomePage;
import page.AdminPages.LoginAdminPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;
import page.UserPages.MenuBar;
import page.UserPages.UserInfoPage;

import java.time.Duration;

public class TC12_SearchCustomer {
    WebDriver driver;
    SoftAssert softAssert;

    LoginAdminPage loginAdminPage;
    LoginPage loginPage;

    HomePage homePage;
    MenuBar menuBar;
    UserInfoPage userInfoPage;
    CustomerListPage customerListPage;
    BankAccountsPage bankAccountsPage;


    String originalHandle;
    String phoneNumber;
    String actualPhoneNumber;
    CustomerListPage.CustomerData selectedCustomer;
    CustomerListPage.CustomerData returnedCustomer;




    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        softAssert = new SoftAssert();

        loginAdminPage = new LoginAdminPage(driver);
        loginPage = new LoginPage(driver);

        homePage = new HomePage(driver);
        menuBar = new MenuBar(driver);
        userInfoPage = new UserInfoPage(driver);
        customerListPage = new CustomerListPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);


        driver.get(Constants.ADMIN_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        originalHandle = driver.getWindowHandle();
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - Search customer by multiple fields")
    public void searchCustomer() {
        loginAdminPage.loginAdmin(Constants.ADMIN_ID,Constants.ADMIN_PASSWORD);

        //Nhap data tim kiem
        homePage.openCustomerListPage();
        selectedCustomer = customerListPage.getCustomerDataFromRow(1); // Chọn hàng đầu tiên

        // Step 5: Nhập thông tin vào các trường tìm kiếm
        customerListPage.enterCustomerID(selectedCustomer.customerId);
        customerListPage.enterFullName(selectedCustomer.fullName);
        customerListPage.enterAddress(selectedCustomer.address);
        customerListPage.enterCMDN(selectedCustomer.cmdn);
        customerListPage.enterPhoneNumber(selectedCustomer.phoneNumber);

        // Step 6: Lấy dữ liệu từ kết quả tìm kiếm và kiểm tra
        returnedCustomer = customerListPage.getCustomerDataFromRow(1);

        //kiem tra ket qua hien thi
        softAssert.assertEquals(returnedCustomer.customerId, selectedCustomer.customerId, "Customer ID mismatch");
        softAssert.assertEquals(returnedCustomer.fullName, selectedCustomer.fullName, "Full Name mismatch");
        softAssert.assertEquals(returnedCustomer.address, selectedCustomer.address, "Address mismatch");
        softAssert.assertEquals(returnedCustomer.cmdn, selectedCustomer.cmdn, "CMDN mismatch");
        softAssert.assertEquals(returnedCustomer.phoneNumber, selectedCustomer.phoneNumber, "Phone Number mismatch");

        softAssert.assertAll();
    }
}
