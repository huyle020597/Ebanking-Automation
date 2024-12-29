package example.AdminTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.AdminPages.CustomerListPage;
import page.AdminPages.HomePage;
import page.AdminPages.LoginAdminPage;
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
    User selectedUser;
    Faker faker;


    String originalHandle;

    User returnedUser;




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
        faker = new Faker();


        driver.get(Constants.ADMIN_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        originalHandle = driver.getWindowHandle();
    }

//    @AfterMethod
//    public void cleanUp() {
//        driver.quit();
//    }

    @Test
            (description = "Admin - Search customer by multiple fields")
    public void searchCustomer() throws InterruptedException {
        loginAdminPage.loginAdmin(Constants.ADMIN);

        //Nhap data tim kiem
        homePage.openCustomerListPage();
        selectedUser = customerListPage.getCustomerDataFromRow(faker.number().numberBetween(1,10));
        // Step 5: Nhập thông tin vào các trường tìm kiếm
        customerListPage.enterCustomerID(selectedUser.getUserId());
        customerListPage.enterFullName(selectedUser.getFullName());
        customerListPage.enterAddress(selectedUser.getCity());
        customerListPage.enterCMDN(selectedUser.getCmnd());
        customerListPage.enterPhoneNumber(selectedUser.getPhoneNumber());

        // Step 6: Lấy dữ liệu từ kết quả tìm kiếm và kiểm tra
        Thread.sleep(2000);
        returnedUser = customerListPage.getCustomerDataFromRow(1);

        //kiem tra ket qua hien thi
        softAssert.assertEquals(returnedUser.getUserId(), selectedUser.getUserId(), "Customer ID mismatch");
        softAssert.assertEquals(returnedUser.getFullName(), selectedUser.getFullName(), "Full Name mismatch");
        softAssert.assertEquals(returnedUser.getCity(), selectedUser.getCity(), "Address mismatch");
        softAssert.assertEquals(returnedUser.getCmnd(), selectedUser.getCmnd(), "CMDN mismatch");
        softAssert.assertEquals(returnedUser.getPhoneNumber(), selectedUser.getPhoneNumber(), "Phone Number mismatch");

        softAssert.assertAll();
    }
}
