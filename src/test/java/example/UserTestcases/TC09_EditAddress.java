package example.UserTestcases;

import com.github.javafaker.Faker;
import model.Constants;
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

public class TC09_EditAddress {
    WebDriver driver ;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    BankAccountsPage bankAccountsPage;
    UserInfoPage userInfoPage;
    Faker faker;
    String addressInput;

    @BeforeMethod
    public void initData () {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        userInfoPage = new UserInfoPage(driver);
        faker = new Faker();
        addressInput = faker.address().fullAddress();

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void cleanUp () {
        driver.quit();
    }

    @Test
    public void editAddress () {
        loginPage.login("huyle020597", "Maddie123@");

        menuBar.openUserInfoPage();

//        System.out.println(userInfoPage.getAddressValue());

        userInfoPage.editAddress(addressInput);

        softAssert.assertEquals(userInfoPage.getAddressValue(),addressInput);
        softAssert.assertAll();
    }
}
