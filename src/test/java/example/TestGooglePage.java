package example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.GooglePage;
import page.LoginPage;

public class TestGooglePage extends TestBase {

    @BeforeMethod(description = "Open the Google page")
    public void openGooglePage() {
        googlePage = new GooglePage(driver);

        driver.get("https://www.google.com");
    }

    @Test(
            description = "Verify that the Google page is displayed correctly"
    )
    public void testGooglePage() {
        Assert.assertTrue(googlePage.isSearchBoxDisplayed(), "The search box is not displayed on the Google page");

        googlePage.search("Selenium");

        Assert.assertEquals(googlePage.getSearchBoxText(), "Selenium", "The search box text is not correct");
    }

    GooglePage googlePage;

}
