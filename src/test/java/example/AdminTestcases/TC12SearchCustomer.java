package example.AdminTestcases;

import modal.Constants;
import org.openqa.selenium.WindowType;
import page.AdminPages.CustomerList;
import page.AdminPages.HomePage;
import page.AdminPages.LoginAdminPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.BankAccountsPage;
import page.UserPages.LoginPage;
import page.UserPages.MenuBar;
import page.UserPages.UserInfoPage;

import java.time.Duration;

public class TC12SearchCustomer {
    WebDriver driver;
    SoftAssert softAssert;

    LoginAdminPage loginAdminPage;
    LoginPage loginPage;

    HomePage homePage;
    MenuBar menuBar;
    UserInfoPage userInfoPage;
    CustomerList customerList;
    BankAccountsPage bankAccountsPage;


    String originalHandle;
    String phoneNumber;



    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        softAssert = new SoftAssert();

        loginAdminPage = new LoginAdminPage(driver);
        loginPage = new LoginPage(driver);

        homePage = new HomePage(driver);
        menuBar = new MenuBar(driver);
        userInfoPage = new UserInfoPage(driver);
        customerList = new CustomerList(driver);
        bankAccountsPage = new BankAccountsPage(driver);



        driver.get(Constants.USER_URL);
        driver.get(Constants.ADMIN_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        originalHandle = driver.getWindowHandle();
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "Admin - Search customer by multiple fields")
    public void searchCustomer() {
        //dang nhap tai khoan user, chon tai khoan va lay so dien thoai
        // loginPage.login(Constants.userId1, Constants.password1);
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml");
        loginPage.login("huyle020597","Maddie123@");

        menuBar.openUserInfoPage();
        phoneNumber = userInfoPage.getUserPhoneNumber();


        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
        loginAdminPage.loginAdmin("1","admin");


        //Nhap data tim kiem
        homePage.openCustomerListPage();
        customerList.enterPhoneNumber(phoneNumber);

        //kiem tra ket qua hien thi
        softAssert.assertEquals(customerList.phoneNumberSearchReturn(phoneNumber),phoneNumber);


        softAssert.assertAll();


    }
}
