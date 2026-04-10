package org.example.pages;
import org.example.BasePage;
import org.example.utils.HelperFunctions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.*;

public class AdvertisementPage extends BasePage {
    @FindBy(xpath = "/html/body/div[2]/main/div/div/div/div[2]/form/div[1]/div[1]/div[1]/div[2]/div/div/div[2]/div")
     List<WebElement> mainElements;
    @FindBy(xpath = "//*[@id=\"content\"]")
    WebElement Extracontent;
    @FindBy(xpath = "/html/body/div[2]/main/header/nav/div/div[3]/div[1]/a")
    WebElement advertisementBtn;
    @FindBy(xpath = "(//div[@class='font-bold font-size-16 text-truncate user-name'][contains(text(),'გიორგი მიქელაძე')])[2]")
    WebElement username;
    @FindBy(xpath = "(//div[contains(@class,'d-flex align-items-center')])[4]")
    WebElement usernameBtn;
    @FindBy(xpath = "(//div[@class='font-bold font-size-16 text-truncate user-name'][contains(text(),'გიორგი მიქელაძე')])[1]")
    WebElement getusername;
    @FindBy(xpath = "/html/body/div[2]/main/div/div/div/div[2]/form/div[1]/div[1]/div[1]/div[2]/div/div/div[1]/div[1]")
     WebElement dropdowncategory;
    @FindBy(xpath = "//*[@id=\"CatID\"]/div/div/div/div[1]/div[1]")
     WebElement title;
    @FindBy(xpath = "(//label[contains(text(),'გაყიდვა')])[1]")
    WebElement sellbtn;
    @FindBy(xpath = "(//label[contains(text(),'შეძენა')])[1]")
    WebElement buybtn;
    @FindBy(xpath = "(//label[contains(text(),'გაქირავება')])[1]")
    WebElement rentbtn;
    @FindBy(xpath = "(//label[contains(text(),'მომსახურება')])[1]")
    WebElement servicebtn;
    @FindBy(xpath = "//*[contains(@id,'react-select-') and contains(@id,'-placeholder')]")
    WebElement branddropdown;
    @FindBy(xpath = "//*[@id=\"BrandID\"]/div/div/div[1]/div[2]")
     WebElement Finddropdownbrand;
    @FindBy(xpath = "//span[contains(@class,'pr-preview-title')]")
    WebElement titletxt;
    @FindBy(xpath = "(//h1[contains(text(),'განცხადების დამატება')])[1]")
     WebElement mainTitle;
    @FindBy(xpath = "(//div[@class='font-bold font-size-16 text-truncate user-name'][contains(text(),'გიორგი მიქელაძე')])[2]")
     WebElement pageusername;
     @FindBy(xpath = "(//div[contains(text(),'ID 9060160')])[2]")
     WebElement usernameID;
     @FindBy(xpath = "//*[@id=\"content\"]/button")
     WebElement Extracontentclosebutton;
     @FindBy(xpath = "//*[@id=\"content\"]/div[3]/button")
     WebElement Extracookiebutton;

    public AdvertisementPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void CloseExtraContent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(Extracontent));
            if (Extracontent.isDisplayed()) {

                waitClick(Extracontentclosebutton);
            }


        } catch (Exception e) {
        }
        try {
            if (Extracookiebutton.isDisplayed()){
                waitClick(Extracookiebutton);
            }

        } catch (Exception e) {
        }


    }

    public  List<WebElement> mainElements(){
        return mainElements;
    }

    public WebElement advertisementBtn(){
        wait.until(ExpectedConditions.visibilityOf(advertisementBtn));
        return advertisementBtn;
    }
    public WebElement getBrandDropdown(){
      //  WebDriverWait localwait = new WebDriverWait(driver, Duration.ofSeconds(2));
       // localwait.until(ExpectedConditions.visibilityOf(branddropdown));
        return branddropdown;
    }

    public WebElement FindDropdownBrand(){
        textWait.until(ExpectedConditions.visibilityOf(Finddropdownbrand));
        return Finddropdownbrand;
    }
    public WebElement getMainTitle(){
        shortWait.until(ExpectedConditions.visibilityOf(mainTitle));
        return mainTitle;
    }

    public WebElement getPageUsername(){
        shortWait.until(ExpectedConditions.visibilityOf(pageusername));

        return pageusername;
    }
    public WebElement getUsernameID(){
        shortWait.until(ExpectedConditions.visibilityOf(usernameID));
        return usernameID;
    }
    public String usernameCheck() {
        shortWait.until(ExpectedConditions.visibilityOf(username));
        return username.getText();
    }

    public String getUserNamecheck() {
        wait.until(ExpectedConditions.visibilityOf(usernameBtn));
        scroll(usernameBtn);
        click(usernameBtn);
        return getusername.getText();
    }

    public List<WebElement> createList() {
        By optionLocator = By.xpath("//div[contains(@id,'react-select-3-option-')]");
        List<WebElement> options = shortWait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(optionLocator)
        );
        return options;


    }
   public WebElement  getDropdownCategory(){
       wait.until(ExpectedConditions.visibilityOf(dropdowncategory));
        return dropdowncategory;
   }

    public boolean checkSellBTNIfClickable() {
        shortWait.until(ExpectedConditions.visibilityOf(sellbtn));
        return sellbtn.isEnabled();
    }

    public void clickSellBTN() {
        shortWait.until(ExpectedConditions.visibilityOf(sellbtn));
        click(sellbtn);
    }

    public void clickRent() {
        shortWait.until(ExpectedConditions.visibilityOf(rentbtn));
        click(rentbtn);
    }

    public void clickService() {
        shortWait.until(ExpectedConditions.visibilityOf(servicebtn));
        click(servicebtn);
    }

    public boolean checkBuyBTNIfClickable() {
        shortWait.until(ExpectedConditions.visibilityOf(buybtn));
        return buybtn.isEnabled();
    }

    public void clickBuyBTN() {
        shortWait.until(ExpectedConditions.visibilityOf(buybtn));
        click(buybtn);
    }

    public boolean checkRentBTNIfClickable() {
        shortWait.until(ExpectedConditions.visibilityOf(rentbtn));
        return rentbtn.isEnabled();
    }

    public boolean checkServiceBTNIfClickable() {
        shortWait.until(ExpectedConditions.visibilityOf(servicebtn));
        return servicebtn.isEnabled();
    }

    public boolean ClickDropdown() {
        shortWait.until(ExpectedConditions.visibilityOf(dropdowncategory));
        boolean pass = dropdowncategory.isEnabled();
        if (pass) {
            click(dropdowncategory);
        }
        return pass;
    }

    public List<WebElement> createBrandList() {
        List<WebElement> optionsList = shortWait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//*[contains(@id,'react-select') and contains(@id,'-option')]")
                )
        );
        return optionsList;
    }

    public void backClick() {
        createList();
        scroll(createList().get(0));
        waitClick(createList().get(0));
    }

    public void waitString(WebElement element) {
        textWait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(element, "აირჩიე/ჩაწერე კატეგორია")
        ));
    }

    public String getTextTitle(){
        try {

            textWait.until(ExpectedConditions.visibilityOf(titletxt));
            String str = titletxt.getText();
            textWait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(titletxt, str)));
            return titletxt.getText();
        } catch (Exception e) {}
        return titletxt.getText();
    }

    public void clickDropdownCategory() {
        shortWait.until(ExpectedConditions.visibilityOf(dropdowncategory));
        scroll(dropdowncategory);
        waitClick(dropdowncategory);

    }
    public WebElement getTitle(){
        String old = title.getText();
       if(!title.getText().equals(old)){
            textWait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(title, old)));
       return title;
        }else {
            return title;
       }
    }

    public void waitClick(WebElement element) {
        shortWait.until(ExpectedConditions.visibilityOf(element));
        shortWait.until(driver -> {
            Map<String, Object> rect = (Map<String, Object>) ((JavascriptExecutor) driver)
                    .executeScript(
                            "var rect = arguments[0].getBoundingClientRect();" +
                                    "return {top: rect.top, bottom: rect.bottom, height: window.innerHeight};",
                            element
                    );
            long top = ((Number) rect.get("top")).longValue();
            long bottom = ((Number) rect.get("bottom")).longValue();
            long height = ((Number) rect.get("height")).longValue();

            return top >= 0 && bottom <= height;
        });
        try { shortWait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (StaleElementReferenceException e) {}
    }

    public String getSplitString(String str){
        String[] arr = str.split(" -> ");
   return arr[arr.length-1];
    }

}











