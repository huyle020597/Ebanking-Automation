package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MenuBar {
    By openAccountBtnLocator ;



    WebDriver driver;

    public MenuBar(WebDriver driver) {
        this.driver = driver;
    }

    private void clickMenuButtons (String text) {
        openAccountBtnLocator = By.xpath(String.format("//li[@role='menuitem']/a/span[text()='%s']",text));
        driver.findElement(openAccountBtnLocator).click();
    }

    public void clickOpenAccount () {clickMenuButtons("Mở tài khoản");}

}
