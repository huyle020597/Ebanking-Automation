package page.UserPages;

import io.qameta.allure.Step;
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

    @Step("Open Open_Account page")
    public void openOpenAccountPage() {clickMenuButtons("Mở tài khoản");}

    @Step ("Open User Information page")
    public void openUserInfoPage() {driver.findElement(By.xpath("(//li[@role='menuitem']/a/span)[2]")).click();};

    @Step ("Open Account page")
    public void openAccountPage() {clickMenuButtons("Tài khoản");}

    @Step ("Open Internal transfer page")
    public void openTransferPage() {
        clickMenuButtons("Chuyển  khoản");
    }

    @Step ("Open External Transfer page")
    public void openExternalTransferPage() {clickMenuButtons("Liên Ngân Hàng");}

    @Step ("Log out")
    public void LogOut() {clickMenuButtons("Đăng xuất");}

    @Step ("Open Transactions page")
    public void openTransactionsPage () {clickMenuButtons("Nhật kí giao dịch");}

    @Step ("Open Change password page")
    public void openChangePWPage () {clickMenuButtons("Đổi mật khẩu");}
}
