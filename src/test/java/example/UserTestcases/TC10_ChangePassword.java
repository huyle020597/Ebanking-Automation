package example.UserTestcases;

import com.github.javafaker.Faker;
import model.Constants;
import model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.UserPages.*;

import java.time.Duration;

public class TC10_ChangePassword {
    WebDriver driver;
    RegisterPage registerPage;
    LoginPage loginPage;
    SoftAssert softAssert;
    Faker faker;
    YopmailPage yopmailPage;
    ChangePasswordPage changePasswordPage;
    BankAccountsPage bankAccountsPage;
    UserInfoPage userInfoPage;
    User newUser;


    String account;
    String oldPassword;
    String newPassword;
    String fullName;
    String phoneNumber;
    String dob;
    String city;
    String id;
    String email;

    String originalHandle;


    @BeforeMethod
    public void initData() {
        driver = new ChromeDriver();
        softAssert = new SoftAssert();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        changePasswordPage = new ChangePasswordPage(driver);
        bankAccountsPage = new BankAccountsPage(driver);
        userInfoPage = new UserInfoPage(driver);

        yopmailPage = new YopmailPage(driver);
        faker = new Faker();

        account = faker.name().username();
        oldPassword = "Test@1234";
        newPassword = "Maddie123@";
        fullName = faker.name().fullName();
        phoneNumber = faker.phoneNumber().subscriberNumber(10);
        dob = "02/05/1997";
        city = "Quang Nam";
        id = faker.idNumber().valid();
        email = "test" + System.currentTimeMillis() + "@yopmail.com";

        newUser = new User(account,oldPassword,email);
        newUser.setFullName(fullName);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setDob(dob);
        newUser.setCity(city);
        newUser.setCmnd(id);


        driver.get(Constants.USER_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        originalHandle = driver.getWindowHandle();

    }


//    @AfterMethod
//    public void cleanUp() {
//        driver.quit();
//    }

    @Test
            (description = "User - Change PW successfully")
    public void TC13() throws InterruptedException {
        // Step 1: Register new account
        loginPage.clickRegisterBtn();
        registerPage.registerAccountByUser(newUser);
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
        loginPage.login(newUser);
        bankAccountsPage.openChangePWPage();


        //Step 4: Change PW
        changePasswordPage.changePassword(oldPassword,newPassword);
        softAssert.assertTrue(changePasswordPage.isSuccessMessageDisplayed());
        changePasswordPage.closeSuccessMessage();
        newUser.setPassword(newPassword);

        //Step5: Logout and login with new PW
        changePasswordPage.LogOut();
        loginPage.login(newUser);
        softAssert.assertEquals(userInfoPage.getUserProfileName(),newUser.getFullName());

        softAssert.assertAll();
    }
}