package page.AdminPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Login {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        driver.manage().window().maximize();


        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");

        loginPage.loginAdmin("1","admin");
    }
}

