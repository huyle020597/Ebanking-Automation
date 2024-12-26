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
import page.AdminPages.HomePage;
import page.UserPages.LoginPage;
import page.UserPages.RegisterPage;
import page.UserPages.UserInfoPage;
import page.UserPages.YopmailPage;

import java.time.Duration;

public class TC13_RegisterCustomerAccountSuccessfully {
    WebDriver driver;
    RegisterPage registerPage;
    LoginPage loginPage;
    UserInfoPage userInfoPage;
    SoftAssert softAssert;
    Faker faker;
    YopmailPage yopmailPage;

    String account;
    String password;
    String fullName;
    String phoneNumber;
    String dob;
    String city;
    String cmnd;
    String email;

    String originalHandle;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        softAssert = new SoftAssert();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        userInfoPage = new UserInfoPage(driver);

        yopmailPage = new YopmailPage(driver);
        faker = new Faker();

        account = faker.name().username();
        password = "Test@1234";
        fullName = faker.name().fullName();
        phoneNumber = faker.phoneNumber().subscriberNumber(10);
//        dob = String.valueOf(faker.date().birthday());
        dob = "02/05/1997";
        city = "Quang Nam";
        cmnd = faker.idNumber().valid();
        email = "test" + System.currentTimeMillis() + "@yopmail.com";

        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        originalHandle = driver.getWindowHandle();

    }


    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
            (description = "User - Register customer account successfully")
    public void TC13() throws InterruptedException {
        // Step 1: Register new account
        loginPage.clickRegisterBtn();
        registerPage.registerAccount(account, password, password, fullName, phoneNumber, dob, city, cmnd, email);
        softAssert.assertTrue(registerPage.isSuccessfulMsgDisplayed());
        registerPage.clickCloseMsg();


        // Step 2: Open new tab for email confirmation
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constants.YOPMAIL_URL);
        String activateURL = yopmailPage.getActivateURL(email);
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(activateURL);


        // Step 3: Login with the newly created account
        driver.switchTo().window(originalHandle);
        loginPage.login(account, password);


        //Step 4: Confirm login with new account
        softAssert.assertEquals(userInfoPage.getUserProfileName(),fullName);
        softAssert.assertAll();
    }
}
