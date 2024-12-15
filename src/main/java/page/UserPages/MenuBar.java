package page.UserPages;

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

    public void openUserInfoPage() {driver.findElement(By.xpath("(//li[@role='menuitem']/a/span)[2]")).click();};

    public void openAccountPage() {clickMenuButtons("Tài khoản");}

    public void openTransferPage() {
        clickMenuButtons("Chuyển  khoản");
    }

    public void openExternalTransferPage() {clickMenuButtons("Liên Ngân Hàng");}

    public void LogOut() {clickMenuButtons("Đăng xuất");}

    public void openTransactionsPage () {clickMenuButtons("Nhật kí giao dịch");}
}
