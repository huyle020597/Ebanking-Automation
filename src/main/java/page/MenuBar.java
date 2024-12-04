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
        openAccountBtnLocator = By.xpath(String.format("//span[@class='ui-menuitem-text'][text()='%s']",text));
        driver.findElement(openAccountBtnLocator).click();
    }

    public void openOpenAccountPage() {clickMenuButtons("Mở tài khoản");}

    public void clickUserInfo () {driver.findElement(By.xpath("(//li[@role='menuitem']/a/span)[2]")).click();};

    public void clickAccountPage () {driver.findElement(By.xpath("(//li[@role='menuitem']/a/span)[4]")).click();}

    public void clickTransferPage () {
        clickMenuButtons("Tài khoản");
    }

    public void clickExternalTransferPage () {clickMenuButtons("Liên Ngân Hàng");}
}
