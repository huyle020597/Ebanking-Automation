package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LinkPage {
    String AdminURL = "http://14.176.232.213:8080/EBankingWebsite/faces/admin/transactions.xhtml";

    WebDriver driver;

    public LinkPage (WebDriver driver) {
        this.driver = driver;
    }

    public void AdminURL() {
        driver.findElement(By.id(AdminURL));
    }


}
