package org.example.Login;
import org.example.BaseTest;
import org.example.pages.AdvertisementPage;
import org.example.pages.LoginPage;
import org.example.utils.AssertHelperManager;
import org.example.utils.ConfigReader;
import org.example.utils.HelperFunctions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.example.utils.ExtentReportManager.*;

@Listeners(value = org.example.utils.TestListener.class)
public class AdvertisementTest extends BaseTest {

    @Test
   public void testAdvertisementCheck() {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        LoginPage loginPage = new LoginPage(driver);
        SoftAssert softAssert = new SoftAssert();
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
       loginPage.Login(ConfigReader.get("login.mail"), ConfigReader.get("login.password"));
        getTest().pass("ავტორიზაცია წარმატებით დასრულდა");
        getTest().info("ნავიგაცია განცხადების დამატების გვერდზე");
        assertHelperManager.navigateToAdvertisementPage(softAssert);
        getTest().info("მომხმარებლის სახელის ვალიდაცია");
        assertHelperManager.hardAssertWithLog(advertisementPage.getUserNamecheck(), advertisementPage.usernameCheck()," username შემოწმება ");

    }

    @Test
    public void testCheckSellBtn() {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        SoftAssert softAssert = new SoftAssert();
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info("ღილაკის შემოწმება");
        assertHelperManager.assertTrueWithLog(softAssert,advertisementPage.checkSellBTNIfClickable(),"ღილაკის შემოწმება");
        getTest().info("ღილაკზე დაკლიკვა");
        advertisementPage.clickSellBTN();
        getTest().info("გაყიდვის ღილაკზე დაჭერის შემდეგომი შემოწმება ");
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("sell.url"), "გაყიდვის");
        softAssert.assertAll();
    }

    @Test
    public void testCheckBuyBtn() {
        SoftAssert softAssert = new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info("ღილაკის შემოწმება");
        assertHelperManager.assertTrueWithLog(softAssert,advertisementPage.checkBuyBTNIfClickable(),"ღილაკის შემოწმება");
        getTest().info("ღილაკზე დაკლიკვა");
        advertisementPage.clickBuyBTN();
        getTest().info("გაყიდვის ღილაკზე დაჭერის შემდეგომი შემოწმება ");
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("buy.url"),"ყიდვის ");
softAssert.assertAll();
    }

    @Test
    public void testCheckRentBtn() {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        SoftAssert softAssert = new SoftAssert();
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info("ღილაკის შემოწმება");
        assertHelperManager.assertTrueWithLog(softAssert,advertisementPage.checkRentBTNIfClickable(),"ღილაკის შემოწმება");
        getTest().info("ღილაკზე დაკლიკვა");
        advertisementPage.clickRent();
        getTest().info("გაყიდვის ღილაკზე დაჭერის შემდეგომი შემოწმება ");
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("rent.url"), "გაქირავების ");
        softAssert.assertAll();
    }

   @Test
   public void testCheckServiceBtn(){
       SoftAssert softAssert = new SoftAssert();
       AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
       AdvertisementPage advertisementPage = new AdvertisementPage(driver);
       loginAndNavigate(softAssert,assertHelperManager);
       getTest().info("ღილაკის შემოწმება");
       assertHelperManager.assertTrueWithLog(softAssert,advertisementPage.checkServiceBTNIfClickable(),"ღილაკის შემოწმება");
       getTest().info("ღილაკზე დაკლიკვა");
        advertisementPage.clickService();
       getTest().info("სერვისების ღილაკზე დაჭერის შემდეგომი შემოწმება ");
       assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("service.url"),"სერვისების ");
softAssert.assertAll();
    }



    @Test(groups = "no-screenshot")
    public void testCheckSellCategory(){
        SoftAssert softAssert = new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაყიდვის ღილაკზე დაკლიკება ");
        advertisementPage.clickSellBTN();
        assertHelperManager.CheckMainAsserts(softAssert,ConfigReader.get("sell.url")," გაყიდვის ");
        getTest().info(" დროპდაუნ მენიუს შემოწმება და დაკლიკება ");
        Assert.assertTrue(advertisementPage.ClickDropdown());
        getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
        getTest().info(" დროპდაუნ მენიუს კატეგორიების list-is შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkMainCategories());
softAssert.assertAll();
    }

    @Test(groups = "no-screenshot")
    public void testCheckCategoryNavigationClick(){
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        SoftAssert softAssert = new SoftAssert();
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაყიდვის ღილაკზე დაკლიკება ");
        advertisementPage.clickSellBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("sell.url"),"გაყიდვის ");
        getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
        advertisementPage.ClickDropdown();
        getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
        getTest().info(" დროპდაუნ მენიუს back - ღილაკის შემოწმება შემოწმება ");
        assertHelperManager.checkBackClickInCategories(softAssert);
softAssert.assertAll();
    }


    @Test(groups = "no-screenshot")
    public void testCheckSellTypeAllItems() {
        SoftAssert softAssert = new SoftAssert();
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        AssertHelperManager assertHelperManager =new AssertHelperManager(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაყიდვის ღილაკზე დაკლიკება ");
        advertisementPage.clickSellBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("sell.url"),"გაყიდვის ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება ");
        assertHelperManager.checkAllCategoryItems(softAssert);
softAssert.assertAll();

    }


    @Test(groups = "no-screenshot")
    public void testCheckSellCategoryData()  {
        SoftAssert softAssert = new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" ყიდვა ღილაკზე დაკლიკება ");
        advertisementPage.clickSellBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("sell.url"),"გაყიდვის ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება მონაცემებთან ");
        assertHelperManager.checkAllCategoryItems(softAssert);

softAssert.assertAll();
    }


    @Test(groups = "no-screenshot")
    public void testCheckSellTypeCategoryDataBrands() {
     SoftAssert softAssert=new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაყიდვის ღილაკზე დაკლიკება ");
        advertisementPage.clickSellBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("sell.url")," გაყიდვა ");
        getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება მონაცემებთან და ბრენდებთან ");
        assertHelperManager.checkAllCategoryItemsDataWithBrands(softAssert);
         softAssert.assertAll();

    }



    @Test(groups = "no-screenshot")
    public void testCheckBuyCategories(){
    SoftAssert softAssert = new SoftAssert();
   AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
    AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
    getTest().info(" ყიდვის ღილაკზე დაკლიკება ");
    advertisementPage.clickBuyBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("buy.url"),"ყიდვის ");
    getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
    advertisementPage.clickDropdownCategory();
    getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
    Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
    getTest().info(" დროპდაუნ მენიუს მთავარი კატეგორიების შემოწმება ");
        assertHelperManager.checkMainCategories();
    softAssert.assertAll();
}



    @Test(groups = "no-screenshot")
    public void testCheckBuyCategoryNavigationClick(){
        SoftAssert softAssert =new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
    getTest().info(" ყიდვის ღილაკზე დაკლიკება ");
        advertisementPage.clickBuyBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("buy.url"),"ყიდვის ");
    getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
        advertisementPage.ClickDropdown();
    getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
    getTest().info(" დროპდაუნ მენიუს back - ღილაკის შემოწმება შემოწმება ");
        assertHelperManager.checkBackClickInCategories(softAssert);
softAssert.assertAll();
    }




    @Test(groups = "no-screenshot")
    public void TestCheckBuyTypeAllItems()  {
        SoftAssert softAssert =new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" ყიდვის ღილაკზე დაკლიკება ");
        advertisementPage.clickBuyBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("buy.url"),"ყიდვის ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება ");
        assertHelperManager.checkAllCategoryItems(softAssert);

softAssert.assertAll();
    }




    @Test(groups = "no-screenshot")
    public void testCheckBuyTypeCategoryData() {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        SoftAssert softAssert =new SoftAssert();
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" ყიდვა ღილაკზე დაკლიკება ");
        advertisementPage.clickBuyBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("buy.url"),"ყიდვის ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება მონაცემებთან ");
        assertHelperManager.checkAllCategoryItems(softAssert);
        softAssert.assertAll();
    }




    @Test(groups = "no-screenshot")
    public void testCheckBuyTypeCategoryDataBrands()  {
        SoftAssert softAssert = new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" ყიდვა ღილაკზე დაკლიკება ");
        advertisementPage.clickBuyBTN();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("buy.url"),"ყიდვის ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება მონაცემებთან და ბრენდებთან ერთად : ");
        assertHelperManager.checkAllCategoryItemsDataWithBrands(softAssert);
softAssert.assertAll();

    }




    @Test(groups = "no-screenshot")
    public void testCheckRentCategories(){
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        SoftAssert softAssert = new SoftAssert();
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაქირავების ღილაკზე დაკლიკება ");
        advertisementPage.clickRent();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("rent.url"),"გაქირაავების ");
        getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
        getTest().info(" დროპდაუნ მენიუს მთავარი კატეგორიების შემოწმება ");
        assertHelperManager.checkMainCategories();
        softAssert.assertAll();
    }




    @Test(groups = "no-screenshot")
    public void testcheckRentCategoryNavigationClick(){
        SoftAssert softAssert = new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაქირავება ღილაკზე დაკლიკება ");
        advertisementPage.clickRent();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("rent.url"),"გაქირავების ");
        getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
        advertisementPage.ClickDropdown();
        getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
        getTest().info(" დროპდაუნ მენიუს back - ღილაკის შემოწმება ");
        assertHelperManager.checkBackClickInCategories(softAssert);
softAssert.assertAll();
    }




    @Test(groups = "no-screenshot")
    public void testcheckRentTypeAllItems()  {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        SoftAssert softAssert = new SoftAssert();
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაქირავება ღილაკზე დაკლიკება ");
        advertisementPage.clickRent();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("rent.url"),"გაქირავების ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება ");
        assertHelperManager.checkAllCategoryItems(softAssert);
softAssert.assertAll();

    }

    @Test(groups = "no-screenshot")
    public void testCheckRentTypeCategoryData()  {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        SoftAssert softAssert = new SoftAssert();
        HelperFunctions helperFunctions = new HelperFunctions(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" გაქირავება ღილაკზე დაკლიკება ");
        advertisementPage.clickRent();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("rent.url"),"გაქირავების ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება მონაცემებთან ");
        helperFunctions.checkAllCategoryItemsData(softAssert);

softAssert.assertAll();
    }


    @Test(groups = "no-screenshot")
    public void testCheckServiceCategories(){
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        SoftAssert softAssert = new SoftAssert();
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" სერვისების ღილაკზე დაკლიკება ");
        advertisementPage.clickService();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("service.url"),"სერვისების ");
        getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
        getTest().info(" დროპდაუნ მენიუს მთავარი კატეგორიების შემოწმება ");
        assertHelperManager.checkMainCategories();
        softAssert.assertAll();
    }


    @Test(groups = "no-screenshot")
    public void checkServiceCategoryNavigationClick(){
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        SoftAssert softAssert = new SoftAssert();
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" სერვისების ღილაკზე დაკლიკება ");
        advertisementPage.clickService();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("service.url"),"სერვისების ");
        getTest().info(" დროპდაუნ მენიუზე დაკლიკება ");
        advertisementPage.ClickDropdown();
        getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        Assert.assertTrue(assertHelperManager.checkCategoryIsVisible());
        getTest().info(" დროპდაუნ მენიუს back - ღილაკის შემოწმება შემოწმება ");
        assertHelperManager.checkBackClickInCategories(softAssert);
softAssert.assertAll();
    }


    @Test(groups = "no-screenshot")
    public void checkServiceTypeCategory() {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        SoftAssert softAssert = new SoftAssert();
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info(" სერვისების ღილაკზე დაკლიკება ");
        advertisementPage.clickService();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("service.url"),"სერვისების ");
        getTest().info(" დროპდაუნ მენიუს კატეგორიების შემოწმება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" კატეგორიების ნახვა ");
        assertHelperManager.checkAllCategoryItems(softAssert);
softAssert.assertAll();
    }


    @Test(groups = "no-screenshot")
    public void testCheckServiceTypeCategoryData()  {
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        SoftAssert softAssert = new SoftAssert();
        HelperFunctions helperFunctions = new HelperFunctions(driver);
        loginAndNavigate(softAssert,assertHelperManager);
        getTest().info("სერვისების ღილაკზე დაკლიკება ");
        advertisementPage.clickService();
        assertHelperManager.CheckMainAsserts(softAssert, ConfigReader.get("service.url"),"სერვისების ");
        getTest().info(" დროპდაუნ კატეგორიებზე დაკლიკება ");
        advertisementPage.clickDropdownCategory();
        getTest().info(" ყველა კატეგორიის შემოწმება მონაცემებთან ");
        helperFunctions.checkAllCategoryItemsData(softAssert);
softAssert.assertAll();

    }






}



