package example;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.*;

public class TC09EditAddress {
    WebDriver driver ;
    LoginPage loginPage;
    SoftAssert softAssert;
    MenuBar menuBar;
    BankAccountsPage bankAccountsPage;
    UserInfoPage userInfoPage;
    Faker faker;

    @BeforeMethod
    public void initData () {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        menuBar = new MenuBar(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        userInfoPage = new UserInfoPage(driver);
        faker = new Faker();

        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#");
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
        String addressInput = faker.address().fullAddress();

        userInfoPage.editAddress(addressInput);


        softAssert.assertEquals(userInfoPage.getAddressValue(),addressInput);
        softAssert.assertAll();
    }
}
