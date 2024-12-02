package example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBase {

    @BeforeClass(description = "Set up the test environment")
    public void setUp() {
        // Set up the test environment
        driver = new ChromeDriver();
    }


    @AfterClass(description = "Tear down the test environment")
    public void tearDown() {
        // Tear down the test environment
        driver.quit();
    }

    protected WebDriver driver;

}
