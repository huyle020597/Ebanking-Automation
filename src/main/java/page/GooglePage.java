package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GooglePage {

    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isSearchBoxDisplayed() {
        return driver.findElement(searchBoxSelector).isDisplayed();
    }

    @Step("Search for text: {text}")
    public void search(String text) {
        driver.findElement(searchBoxSelector).sendKeys(text);
        driver.findElement(searchBoxSelector).submit();
    }

    public String getSearchBoxText() {
        return driver.findElement(searchBoxSelector).getText();
    }

    private WebDriver driver;
    private By searchBoxSelector = By.name("q");
}
