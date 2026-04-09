package org.example;
import org.example.pages.AdvertisementPage;
import org.example.pages.LoginPage;
import org.example.utils.AssertHelperManager;
import org.example.utils.DriverManager;
import org.example.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static org.example.utils.ExtentReportManager.getTest;


public class BaseTest {
    protected WebDriver driver;
    public WebDriverWait wait;
    public WebDriverWait shortWait;

    @BeforeMethod
    public void setUp(){
        driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getlong("long.wait")));
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("base.url"));
        advertisementPage.CloseExtraContent();
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement dialog = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/dialog[2]")));
            //WebElement dialog = wait.until(driver.findElement(By.xpath("/html/body/dialog[2]")));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].close();", dialog
            );
            js.executeScript("document.querySelector('dialog').close();");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void tearDown(){
        DriverManager.quit();

    }

    public void assertstring(String act, String exp){

        if (act.equals(exp)) {
            getTest().pass(( act +" და " + exp + "— შედარება წარმატებულია "));
        } else {
            getTest().fail(act +" და " + exp + "— შედარება წარუმატებელია ");
        }
        Assert.assertEquals(act,exp);
    }

public void loginAndNavigate(SoftAssert softAssert, AssertHelperManager assertHelperManager){
    LoginPage loginPage = new LoginPage(driver);
    getTest().info("დალოგინება");
    loginPage.Login(ConfigReader.get("login.mail"), ConfigReader.get("login.password"));
    getTest().info("ნავიგაცია განცხადების დამატების გვერდზე");
    assertHelperManager.navigateToAdvertisementPage(softAssert);

}

    }


