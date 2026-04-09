package org.example;
import org.example.utils.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    public  WebDriverWait shortWait;
    public WebDriverWait wait;


    public BasePage (WebDriver driver){
        this.driver= driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getlong("long.wait")));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getlong("short.wait")));
        PageFactory.initElements(driver,this);
    }
    public void refreshPage(){
        driver.navigate().refresh();
    }
    public void Sendkeys (WebElement locator, String text){
        waitElementToBeVisible(locator);
        locator.clear();
        locator.sendKeys(text);
    }


    public void waitElementToBeClickable(WebElement locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitElementToBeVisible(WebElement locator) {
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public void click(WebElement locator) {
        waitElementToBeClickable(locator);
        locator.click();
    }

    public String Gettext(WebElement locator){
        waitElementToBeVisible(locator);
        return locator.getText();

    }
    public String getcurrentURL(){
        return driver.getCurrentUrl();
    }
    public void scroll(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

    }





}
