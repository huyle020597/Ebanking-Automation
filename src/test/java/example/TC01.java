package example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.LoginPage;
import page.MenuBar;
import page.OpenAccountPage;

public class TC01 {
        WebDriver driver ;
        LoginPage loginPage;
        SoftAssert softAssert;
        MenuBar menuBar;
        OpenAccountPage openAccountPage;

        @BeforeMethod
    public void initData () {
            driver = new ChromeDriver();
            loginPage = new LoginPage(driver);
            softAssert = new SoftAssert();
            menuBar = new MenuBar(driver);
            openAccountPage = new OpenAccountPage(driver);

            driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#");
            driver.manage().window().maximize();

        }

        @AfterMethod
    public void cleanUp () {
            driver.quit();
        }
// TC tao tai khoan thanh cong
        @Test
    public void openNewAccount () {
            loginPage.login("huyle020597", "Maddie123@");
            menuBar.clickOpenAccount();
            openAccountPage.clickAccTypeDropdown();
            openAccountPage.selectNonTermAcc();
            openAccountPage.clickCreateAccBtn();


            softAssert.assertTrue(openAccountPage.isSuccessfulMsgDisplayed());
            softAssert.assertAll();
        }
    public void openGmail () {

    }
}
