package org.example.utils;
import com.aventstack.extentreports.ExtentTest;
import org.example.BasePage;
import org.example.pages.AdvertisementPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.List;

import static org.example.utils.ExtentReportManager.getTest;

public class HelperFunctions extends BasePage{
    public HelperFunctions(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void EmptyWithDataCheck(SoftAssert softAssert, ExtentTest node) {
        AssertHelperManager assertHelperManager =new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        boolean bool = !advertisementPage.mainElements().isEmpty();
        if (!bool) {
            advertisementPage.waitString(advertisementPage.getTitle());
            System.out.println(advertisementPage.getTitle().getText());
            assertHelperManager.itemToData(advertisementPage.getTitle().getText(),softAssert,node);
            scroll(advertisementPage.getDropdownCategory());
            advertisementPage.waitClick(advertisementPage.getDropdownCategory());
        } else {

            advertisementPage.createList();
            for (int h = 1; h < advertisementPage.createList().size(); h++) {
                advertisementPage.createList();
                WebElement subb = advertisementPage.createList().get(h);
                System.out.println("                    " + subb.getText() + "------");
                scroll(subb);
                advertisementPage.waitClick(subb);
                EmptyWithDataCheck( softAssert,node);
                scroll(advertisementPage.getDropdownCategory());
                advertisementPage.waitClick(advertisementPage.getDropdownCategory());
                advertisementPage.createList();
            }
            advertisementPage.backClick();
        }


    }


    //ბოლო კატეგორიიდან იწყება ბრენდების შემოოწმება, branddropdownfind თან არგუმენტებით დაკავშირება (პოულობს დროპდაუნს)
    //იქმნება რეპორტში ცალკე ქვესექციები შესაბამისი აითემის ბრენდების
    //itemToDataBrands ჩაიტანს არგუმენტებით
    public void EmptyWithDataCheckBrands(SoftAssert softAssert, ExtentTest node) {
        AssertHelperManager assertHelperManager =new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        boolean bool = !advertisementPage.mainElements().isEmpty();
        if (!bool) {
            advertisementPage.waitString(advertisementPage.getTitle());
            try {
                textWait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(advertisementPage.getTitle(), advertisementPage.getTitle().getText())));
            } catch (Exception e) {}
            String titleText = advertisementPage.getTitle().getText();
            ExtentTest itemNode = node.createNode(titleText);
            try {
                List<String> brands = assertHelperManager.brandDropdownFind(node);
                if (brands.isEmpty()) {
                    itemNode.info(titleText + " — ბრენდის dropdown არ არის, გამოტოვება");
                }
                else {
                    ExtentTest brandsNode = itemNode.createNode("ბრენდები");
                    for (String brand : brands) {
                        assertHelperManager.itemToDataBrands(brandsNode, titleText, brand, softAssert);
                    }
                }
            } catch (Exception e) {
                itemNode.info(titleText + " — ბრენდი არ აქვს: " );
            }

            scroll(advertisementPage.getDropdownCategory());
            advertisementPage.waitClick(advertisementPage.getDropdownCategory());
        } else {
            for (int h = 1; h < advertisementPage.createList().size(); h++) {
                String subName = advertisementPage.createList().get(h).getText();
                ExtentTest subNode = node.createNode(subName);
                scroll(advertisementPage.createList().get(h));
                advertisementPage.waitClick(advertisementPage.createList().get(h));
                EmptyWithDataCheckBrands(softAssert, subNode);
                scroll(advertisementPage.getDropdownCategory());
                advertisementPage.waitClick(advertisementPage.getDropdownCategory());
            }
            advertisementPage.backClick();
        }
    }


//სათითაოდ შევა ყველა მთავარ კატეგორიაში შემდეგ ყველა მათ ქვეკატეგორიაში და შეამოწმოს მონაცემების სისწორე.
    //გადავა emptywithdatacheck. სადაც empty შემხვდება ყველგან ნაგულისმევია რომ სანამ არ დამტავრდება და ცარიალი არ იქნება ბოლომდე
    //შევიდეს
    public void checkAllCategoryItemsData(SoftAssert softAssert) {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        ExtentTest validationStep = getTest().createNode("კატეგორიების ნახვა");
        for (int i = 0; i < advertisementPage.createList().size(); i++) {
            WebElement option = advertisementPage.createList().get(i);
            scroll(option);
            advertisementPage.waitClick(option);
            for (int j = 1; j < advertisementPage.createList().size(); j++) {
                WebElement sub = advertisementPage.createList().get(j);
                scroll(sub);
                advertisementPage.waitClick(sub);
                EmptyWithDataCheck(softAssert,validationStep);
            }
            advertisementPage.backClick();
            advertisementPage.createList();
            if (advertisementPage.createList().get(0).getText().contains("უკან დაბრუნება")) {
                advertisementPage.backClick();
            }
        }

        advertisementPage.backClick();

    }

public String titletext(WebElement locator) {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
    WebDriverWait localwait = new WebDriverWait(driver, Duration.ofSeconds(1));
    String titletext =locator.getText();
    try {
        localwait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(locator, titletext)));
        return advertisementPage.getSplitString(locator.getText());
    } catch (Exception e) {
        return advertisementPage.getSplitString(locator.getText());
    }}

}
