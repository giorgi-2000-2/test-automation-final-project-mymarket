package org.example.utils;
import com.aventstack.extentreports.ExtentTest;
import org.example.BasePage;
import org.example.pages.AdvertisementPage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.example.utils.ExtentReportManager.getTest;

public class AssertHelperManager extends BasePage{

    public AssertHelperManager(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void ifEmpty(SoftAssert softAssert,ExtentTest node) {
        HelperFunctions helperFunctions = new HelperFunctions(driver);
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        boolean bool = !advertisementPage.mainElements().isEmpty();
        if (!bool) {
            WebElement title = shortWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"CatID\"]/div/div/div/div[1]/div[1]"))));
            assertWithLog( softAssert, node,helperFunctions.titletext(title),(advertisementPage.getTextTitle())," შედარება ");
            scroll(advertisementPage.getDropdownCategory());
            advertisementPage.waitClick(advertisementPage.getDropdownCategory());
        } else {
            for (int h = 1; h < advertisementPage.createList().size(); h++) {
                WebElement subb = advertisementPage.createList().get(h);
                System.out.println("                    " + subb.getText() + "------");
                scroll(subb);
                advertisementPage.waitClick(subb);
                ifEmpty(softAssert,node);
                scroll(advertisementPage.getDropdownCategory());
                advertisementPage.waitClick(advertisementPage.getDropdownCategory());
                advertisementPage.createList();
            }
            advertisementPage.backClick();
        }



    }

    public List<String> brandDropdownFind(ExtentTest node) {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        List<String> brandNameList = new ArrayList<>();
        boolean hasBrand;
        try {
            hasBrand = advertisementPage.getBrandDropdown().isDisplayed();

            if (hasBrand) {
                scroll(advertisementPage.FindDropdownBrand());
                advertisementPage.waitClick(advertisementPage.FindDropdownBrand());
                List<WebElement> brandList = advertisementPage.createBrandList();

                try{
                    for (int i = 0; i < brandList.size(); i++) {
                        String text = brandList.get(i).getText().trim();
                        if (i == 0 && text.equals("-")) {
                            continue;
                        }
                        scroll(brandList.get(i));
                        brandNameList.add(text);
                    }}
                catch (Exception e) {
                    node.info("ნაკლები ბრენდია ");
                }


                advertisementPage.waitClick(advertisementPage.FindDropdownBrand());
            }
        } catch (Exception e) {

        }

        return brandNameList;
    }
//შემოწმების პირველი ციკლები, EmptyWithDataCheckBrands თან არგუმენტების გადაცემა...
    public void checkAllCategoryItemsDataWithBrands(SoftAssert softAssert) {
HelperFunctions helperFunctions = new HelperFunctions(driver);
AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        ExtentTest validationStep = getTest().createNode("ბრენდების შემოწმება");

        for (int i = 0; i < 3; i++) {
            String categoryName = advertisementPage.createList().get(i).getText();
            ExtentTest categoryNode = validationStep.createNode(categoryName);

            scroll(advertisementPage.createList().get(i));
            advertisementPage.waitClick(advertisementPage.createList().get(i));

            for (int j = 1; j < 3; j++) {
               // String subName = advertisementPage.createlist().get(j).getText();
                scroll(advertisementPage.createList().get(j));
                advertisementPage.waitClick(advertisementPage.createList().get(j));
                helperFunctions.EmptyWithDataCheckBrands(softAssert, categoryNode);
            }

            advertisementPage.backClick();
            if (!advertisementPage.createList().isEmpty() &&
                    advertisementPage.createList().get(0).getText().contains("უკან დაბრუნება")) {
                advertisementPage.backClick();
            }
        }

        softAssert.assertAll();
    }
//დაშლის სტრინგს და დაყოფს შესაბამისი რიგითობის და წოდების მიხედვით. შემდეგ მოძებნის ჯეისონში ამათ.
    //SUBCATEGORY თუ არ არის მაშინ პირდაპირ არგუმენტებ გადასცემს findBrandInItemს თუარადა searchBrandInSubcategories იძახებს
    public void itemToDataBrands(ExtentTest node, String name, String brandname, SoftAssert softAssert) {
        JSONObject json = JsonReader.getJson();
        String[] parts = name.split(" -> ");
        String mainCategory = parts[0];
        String itemName = parts[parts.length - 1];
        String[] subCategories = Arrays.copyOfRange(parts, 1, parts.length - 1);

        boolean found = false;
        try {
            String[] sections = {"categories", "rental_categories", "service_categories"};
            for (String section : sections) {
                if (!json.has(section) || !json.getJSONObject(section).has(mainCategory)) continue;
                JSONObject mainCat = json.getJSONObject(section).getJSONObject(mainCategory);
                if (subCategories.length == 0) {
                    found = findBrandInItem(mainCat, itemName, brandname);
                } else {
                    found = searchBrandInSubcategories(mainCat, subCategories, 0,
                            itemName, brandname);
                }
                if (found) break;
            }

            assertTrueWithLog(softAssert, node, found, name + " -> " + brandname);

        } catch (Exception e) {

            node.info(name + " -> " + brandname + " JSON შეცდომა: " + e.getMessage());
        }
    }
//ეძებს ბრენდს აითემის ბრენდების სიაში
    private boolean findBrandInItem(JSONObject cat, String itemName, String brandname) {
        try {
            if (!cat.has("items")) return false;
            JSONObject items = cat.getJSONObject("items");
            if (!items.has(itemName)) return false;
            JSONObject item = items.getJSONObject(itemName);
            if (!item.has("brands")) return false;

            JSONArray brands = item.getJSONArray("brands");
            for (int i = 0; i < brands.length(); i++) {
                if (brands.getString(i).equalsIgnoreCase(brandname.trim())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
//ეს იიღებს არგუმენტებს და რეკურუსიულლად ჩადის
    private boolean searchBrandInSubcategories(JSONObject node, String[] subCats, int index, String itemName, String brandname) {

        try {
            if (index < subCats.length && node.has("subcategories")) {
                JSONObject subcategories = node.getJSONObject("subcategories");
                String target = subCats[index];
                if (subcategories.has(target)) {
                    return searchBrandInSubcategories(
                            subcategories.getJSONObject(target),
                            subCats, index + 1, itemName, brandname);
                }
            }
            return findBrandInItem(node, itemName, brandname);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkCategoryIsVisible() {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);

        boolean bool=false;
        if(!advertisementPage.mainElements().isEmpty()){
            getTest().pass("დროპდაუნ მენიუს კატეგორიები ხილვადია ");
            bool=true;
        }else {
            getTest().fail("დროპდაუნ მენიუს კატეგორიები არ არის ხილვადი ");
        }


        return bool;

    }

    public boolean checkMainCategories() {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        boolean pass;
        List<WebElement> option = advertisementPage.createList();
        if(!option.isEmpty()){
            for (int i = 1; i < advertisementPage.createList().size(); i++) {
                scroll( option.get(i));
            }
            getTest().pass("მთავარი კატეგორიები ხილვადია");
            pass=true;
        }else {
            getTest().fail("მთავარი კატეგორიები არ არის ხილვადი");
            pass=false;
        }
        return pass;

    }
    //ძირითადი ელემენტების შემოწმება SoftAssertით
    public void CheckMainAsserts(SoftAssert softAssert,String url,String str){
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        ExtentTest validationStep = getTest().createNode(str+ " ღილაკზე დაჭერის შემდეგომი შემოწმება");
        assertWithLog(softAssert,validationStep,getcurrentURL(), url, " მისამართის შემოწმება ");
        assertWithLog(softAssert,validationStep,advertisementPage.getUserNamecheck(),advertisementPage.usernameCheck(),"username წარმატებით შემოწმება");
        assertWithLog(softAssert,validationStep,advertisementPage.getMainTitle().getText(),"განცხადების დამატება","- მთავარი სათაური ");
        assertWithLog(softAssert,validationStep,advertisementPage.getPageUsername().getText(),"გიორგი მიქელაძე", "username - გიორგი მიქელაძე ");
        assertWithLog(softAssert,validationStep,advertisementPage.getUsernameID().getText(), ConfigReader.get("user.id"),"ID - "+ ConfigReader.get("user.id"));

    }


    public void assertWithLog(SoftAssert softAssert, ExtentTest node, String actual, String expected, String description) {
        if (actual.equals(expected)) {
            softAssert.assertEquals(actual, expected);
            node.pass(description + " — წარმატებულია: " + actual);
        } else {
            softAssert.assertEquals(actual, expected);
            node.fail(description + " — მოსალოდნელი: " + expected + ", მიღებული: " + actual);
        }
    }


    public void hardAssertWithLog(String actual, String expected, String description) {
        if (actual.equals(expected)) {
            Assert.assertEquals(actual, expected);
            getTest().pass(description + " — წარმატებულია: " + actual);
        } else {
            Assert.assertEquals(actual, expected);
            getTest().fail(description + " — მოსალოდნელი: " + expected + ", მიღებული: " + actual);
        }
    }


    public void assertTrueWithLog(SoftAssert softAssert, boolean answer, String description) {

        softAssert.assertTrue(answer);
        if (answer) {
            getTest().pass((description + " — კლიკირებადია"));
        } else {
            getTest().fail(description + " — არ არის კლიკირებადი");
        }
    }

    public void assertTrueWithLog(SoftAssert softAssert,ExtentTest node, boolean answer, String description) {

        softAssert.assertTrue(answer);
        if (answer) {
            node.pass((description + " წარმატებულია "));
        } else {
            node.fail(description + " წარუმატებელია ");
        }
    }


    public void checkBackClickInCategories(SoftAssert softAssert) {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        ExtentTest validationStep = getTest().createNode("დროპდაუნ მენიუს კატეგორიები ხილვადია");
        List<String> Oldlistcheck = new ArrayList<>();
        int size1 = advertisementPage.createList().size();
        for (int i = 0; i < size1; i++) {
            WebElement option = advertisementPage.createList().get(i);
            Oldlistcheck.add(option.getText());
            scroll(option);
            click(option);
            int llist = advertisementPage.createList().size();
            for (int j = 1; j < llist; j++) {
                validationStep.info("შესადარებელი კატეგორიები " + Oldlistcheck.get(i) + " არ უდრის  "+ advertisementPage.createList().get(j).getText()+" –ს ");
                assertTrueWithLog(softAssert, validationStep,!Oldlistcheck.get(i).equals(advertisementPage.createList().get(j).getText()), " ნავიგაცია ");
            }

            advertisementPage.backClick();
        }
        softAssert.assertAll();
    }


    public void itemToData(String name,SoftAssert softAssert, ExtentTest node) {
        boolean found = false;
        //ჯეისონში ჩაიყვანს
        try {
            String jsonContent = new String(Files.readAllBytes(
                    Paths.get("src/test/category.Json")));
            JSONObject json = new JSONObject(jsonContent);

            String[] parts = name.split(" -> ");
            String mainCategory = parts[0];
            String itemName = parts[parts.length - 1];
            String[] subCategories = Arrays.copyOfRange(parts, 1, parts.length - 1);
            found = false;

            // სამივე სექციაში ცალ-ცალკე ვეძებთ
            String[] sections = {"categories", "rental_categories", "service_categories"};

            for (String section : sections) {
                if (!json.getJSONObject(section).has(mainCategory)) {
                    continue;
                }
                JSONObject mainCat = json.getJSONObject(section).getJSONObject(mainCategory);


                if (subCategories.length == 0) {
                    if (mainCat.has("items")) {
                        found = mainCat.getJSONObject("items").has(itemName);

                    }
                } else {

                    found = searchInSubcategories(mainCat, subCategories, 0, itemName, softAssert);
                }

                if (found) break;
            }


            assertTrueWithLog(softAssert, node,found,name + " მოიძებნა ");
            System.out.println(name);

        } catch (Exception e) {
            assertTrueWithLog(softAssert,found, " moZebna ");
            getTest().fail("ვერ მოიძებნა"+ name);
        }


    }

    private boolean searchInSubcategories(JSONObject nodes, String[] subCats, int index,String itemName, SoftAssert softAssert) {
        if (!nodes.has("subcategories")) return false;

        JSONObject subcategories = nodes.getJSONObject("subcategories");
        String targetSub = subCats[index];

        if (!subcategories.has(targetSub)) return false;

        JSONObject subNode = subcategories.getJSONObject(targetSub);

        if (index == subCats.length - 1) {

            if (subNode.has("items")) {
                return subNode.getJSONObject("items").has(itemName);
            }
            return false;
        } else {

            return searchInSubcategories(subNode, subCats, index + 1, itemName, softAssert);
        }
    }


    public void navigateToAdvertisementPage(SoftAssert softAssert)  {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        click(advertisementPage.advertisementBtn());
        wait.until(ExpectedConditions.urlToBe("https://www.mymarket.ge/ka/pr-form/"));
        softAssert.assertEquals(getcurrentURL(), "https://www.mymarket.ge/ka/pr-form/");
        softAssert.assertEquals(advertisementPage.getMainTitle().getText(),"განცხადების დამატება");
        softAssert.assertEquals(advertisementPage.getPageUsername().getText(),"გიორგი მიქელაძე");
        softAssert.assertEquals(advertisementPage.getUsernameID().getText(),"ID 9060160");
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelector('dialog').close();");} catch (Exception e) {
        }
    }


    public void checkAllCategoryItems(SoftAssert softAssert) {
        AdvertisementPage advertisementPage = new AdvertisementPage(driver);
        ExtentTest validationStep = getTest().createNode("კატეგორიების ნახვა");
        advertisementPage.createList();
        for (int i = 0; i < 3; i++) {
            scroll(advertisementPage.createList().get(i));
            advertisementPage.waitClick(advertisementPage.createList().get(i));
            advertisementPage.createList();
            for (int j = 1; j < 3; j++) {
                advertisementPage.createList();
                WebElement sub = advertisementPage.createList().get(j);
                scroll(sub);
                advertisementPage.waitClick(sub);
                ifEmpty(softAssert,validationStep);
            }
            advertisementPage.backClick();
            advertisementPage.createList();
            if (advertisementPage.createList().get(0).getText().contains("უკან დაბრუნება")) {
                advertisementPage.backClick();
            }
        }

        advertisementPage.backClick();
        softAssert.assertAll();
    }





}




