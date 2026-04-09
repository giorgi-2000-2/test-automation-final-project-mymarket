package org.example.Api;
import io.restassured.response.Response;
import org.example.utils.APIManager;
import org.example.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.example.utils.ExtentReportManager.getTest;
@Listeners(value = org.example.utils.TestListener.class)
public class ApiTest {

    @Test(groups = "no-screenshot")
    public void testGetProductE2ETest() {
        APIManager apiManager = new APIManager();
        String title = apiManager.uniqueTitle(ConfigReader.get("UNIQ.TITLE"));
        Map<String, Object> createBody = new HashMap<>();
        createBody.put("title", title);
        createBody.put("price", Integer.parseInt(ConfigReader.get("DEF.PRICE")));
        createBody.put("description", ConfigReader.get("DEF.DESCRIPTION"));
        createBody.put("categoryId", ConfigReader.get("DEF.CATID"));
        createBody.put("images", new String[]{ConfigReader.get("DEF.IMAGE")});
        getTest().info("მომზადდა სატესტო მონაცემები ");

        getTest().info(" პროდუქტის შექმნა");
        Response created = apiManager.post("/products", apiManager.getHeaders(), createBody);

        apiManager.assertWithLog(created.statusCode(), 201, "პროდუქტიs შექმნა");
        int id = created.jsonPath().getInt("id");

        getTest().info("პროდუქტის წაკითხვა და შემოწმება");
        Response fetched = apiManager.get("/products/{id}", apiManager.getHeaders(), id);
        apiManager.assertWithLog(fetched.jsonPath().getString("title"), title, "სათაური ");
        apiManager.assertWithLog(fetched.jsonPath().getInt("price"), Integer.parseInt(ConfigReader.get("DEF.PRICE")), "ფასი ");
        getTest().info("პროდუქტის განახლება");
        Map<String, Object> updateBody = new HashMap<>();
        String updatetitle = apiManager.uniqueTitle("განახლებული");
        updateBody.put("title", updatetitle);
        updateBody.put("price", 55);
        updateBody.put("description", "Updated description text");
        updateBody.put("categoryId", 1);
        updateBody.put("images", new String[]{"https://placehold.co/600x400"});
        Response updated = apiManager.put("/products/{id}", apiManager.getHeaders(), id, updateBody);
        apiManager.assertWithLog(updated.statusCode(), 200, "განახლება ჩავარდა");
        apiManager.assertWithLog(updated.jsonPath().getString("title"), updatetitle, "განახლებული სათაური ");
        getTest().info(" პროდუქტის წაშლა");
        apiManager.delete("/products/{id}", apiManager.getHeaders(), id);
        getTest().info(" წაშლის დადასტურება");
        Response verify = apiManager.get("/products/{id}", apiManager.getHeaders(), id);
        apiManager.assertWithLog(verify.statusCode(), 404, "წაშლის შემდგომი შემოწმება ");

    }

    @Test(groups = "no-screenshot")
    public void testGetProductById() {
        APIManager apiManager = new APIManager();

        Map<String, Object> body = new HashMap<>();
        String expectedTitle = apiManager.uniqueTitle("get-by-id"); // ← ერთხელ
        body.put("title", expectedTitle);
        body.put("price", 10);
        body.put("description", "desc");
        body.put("categoryId", 1);
        body.put("images", new String[]{"https://placehold.co/600x400"});
        Response created = apiManager.post("/products", apiManager.getHeaders(), body);
        Assert.assertEquals(created.statusCode(), 201, "Setup: product creation failed");

        int id = created.jsonPath().getInt("id");
        Response response = apiManager.get("/products/{id}", apiManager.getHeaders(), id);
        apiManager.assertWithLog(response.jsonPath().getInt("id"), id," შედარება ");
        apiManager.assertWithLog(response.jsonPath().getString("title"), expectedTitle," შედარება ");
        apiManager.assertWithLog(response.jsonPath().getInt("price"), 10," შედარება ");
    }

    @Test(groups = "no-screenshot")
    public void testGetProductsWithQuery() {
        APIManager apiManager = new APIManager();
        Map<String, Object> params = new HashMap<>();
        params.put("offset", 0);
        params.put("limit", 5);
        getTest().info(" პროდუქტების ლიმიტია – 5");
        Response response = apiManager.getParams("/products", apiManager.getHeaders(), params);
        List<Map<String, Object>> products = response.jsonPath().getList("");
        getTest().info("სტატუს კოდის და სიის შემოწმება");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(products.size() <= 5, "პროდუქტების რაოდენობა 5-ზე მეტია");
        Assert.assertFalse(products.isEmpty(), "პროდუქტების სია ცარიელია");
        getTest().info("ყველა პროდუქტის ველების შემოწმება — " + products.size() + " პროდუქტი");
        for (Map<String, Object> product : products) {
            Assert.assertNotNull(product.get("id"), "id არ არის");
            Assert.assertNotNull(product.get("title"), "title არ არის");
            Assert.assertNotNull(product.get("price"), "price არ არის");
        }
    }


    @Test(groups = "no-screenshot")
    public void testCreateProduct() {
        APIManager apiManager = new APIManager();
        String expectedTitle=apiManager.uniqueTitle("test-product");
        Map<String, Object> body = new HashMap<>();
        body.put("title", expectedTitle);
        body.put("price", 100);
        body.put("description", "desc");
        body.put("categoryId", 1);
        body.put("images", new String[]{ConfigReader.get("DEF.IMAGE")});
        getTest().info("პროდუქტის შექმნა — title: " + expectedTitle);
        Response response = apiManager.post("/products", apiManager.getHeaders(), body);
        apiManager.assertWithLog(response.statusCode(), 201," შედარება ");
        apiManager.assertWithLog(response.jsonPath().getString("title"),expectedTitle," შედარება ");
        getTest().pass("პროდუქტი შეიქმნა — id: " + response.jsonPath().getInt("id"));
    }


    @Test(groups = "no-screenshot")
    public void testCreateWithoutPrice() {
        APIManager apiManager = new APIManager();
        Map<String, Object> body = new HashMap<>();
        body.put("title", "wrong");
        getTest().info("პროდუქტის შექმნა ფასის გარეშე");
        Response response = apiManager.post("/products", apiManager.getHeaders(), body);
        apiManager.assertWithLog(response.statusCode(), 500," შედარება ");
    }

    @Test(groups = "no-screenshot")
    public void testDeleteProduct() {
        APIManager apiManager = new APIManager();
        Map<String, Object> body = new HashMap<>();
        body.put("title", apiManager.uniqueTitle("to-delete"));
        body.put("price", 10);
        body.put("description", "desc");
        body.put("categoryId", 1);
        body.put("images", new String[]{"https://placehold.co/600x400"});
        getTest().info("პროდუქტის შექმნა");
        Response created = apiManager.post("/products", apiManager.getHeaders(), body);
        int id = created.jsonPath().getInt("id");
        apiManager.delete("/products/{id}", apiManager.getHeaders(), id);
        getTest().info("პროდუქტის წაშლა — id: " + id);
        Response deleted = apiManager.get("/products/{id}", apiManager.getHeaders(), id);
        getTest().info("წაშლის დადასტურება — id: " + id);
        apiManager.assertWithLog(deleted.statusCode(), 404, "წაშლა დადასტურდა");
    }


    @Test(groups = "no-screenshot")
    public void testCreateAndGetE2E() {
        APIManager apiManager = new APIManager();
        String title = apiManager.uniqueTitle("e2e-test");

        Map<String, Object> body = new HashMap<>();
        body.put("title", title);
        body.put("price", 50);
        body.put("description", "desc");
        body.put("categoryId", 1);
        body.put("images", new String[]{"https://placehold.co/600x400"});
        getTest().info("პროდუქტის შექმნა " + title);
        Response post = apiManager.post("/products", apiManager.getHeaders(), body);
        Assert.assertEquals(post.statusCode(), 201, "POST failed: " + post.body().asString());
        int id = post.jsonPath().getInt("id");
        getTest().info("პროდუქტის წაკითხვა " + id);
        Response get = apiManager.get("/products/{id}", apiManager.getHeaders(), id);
        apiManager.assertWithLog(get.jsonPath().getString("title"), title, " შედარება ");
    }

    @Test(groups = "no-screenshot")
    public void testCreateDeleteE2E() {
        APIManager apiManager = new APIManager();
        Map<String, Object> body = new HashMap<>();
        body.put("title", apiManager.uniqueTitle("delete-flow"));
        body.put("price", 10);
        body.put("description", "desc");
        body.put("categoryId", 1);
        body.put("images", new String[]{"https://placehold.co/600x400"});
        getTest().info("პროდუქტის შექმნა " );
        Response post = apiManager.post("/products", apiManager.getHeaders(), body);
        apiManager.assertWithLog(post.statusCode(), 201, " შედარება " );
        int id = post.jsonPath().getInt("id");
        getTest().info("პროდუქტის წაშლა " + id);
        getTest().info("წაშლის დადასტურება " + id);
        apiManager.delete("/products/{id}", apiManager.getHeaders(), id);
        Response getAfterDelete = apiManager.get("/products/{id}", apiManager.getHeaders(), id);
        apiManager.assertWithLog(getAfterDelete.statusCode(), 404, "პროდუქტის წაიშალა – ");


    }


    @Test(groups = "no-screenshot")
    public void testFilterByPriceRange() {
        APIManager apiManager = new APIManager();
        Map<String, Object> params = new HashMap<>();
        params.put("price_min", 10);
        params.put("price_max", 100);
        getTest().info("ფასის ფილტრი —მინიმალური არის =10, მაქსიმალური არის =100");
        Response response = apiManager.getParams("/products", apiManager.getHeaders(), params);
        List<Map<String, Object>> products = response.jsonPath().getList("");
        apiManager.assertWithLog(response.statusCode(), 200," შედარება ");
        Assert.assertFalse(products.isEmpty(), "სია ცარიელია");
        getTest().info("შედეგი არის — " + products.size() + " პროდუქტი");


        for (Map<String, Object> product : products) {
            int price = (int) product.get("price");
            Assert.assertTrue(price >= 10 && price <= 100,
                    "ფასი დიაპაზონს გარეთია: " + price);
        }

    }

    @Test(groups = "no-screenshot")
    public void testFilterByCategoryId() {
        APIManager apiManager = new APIManager();
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", 1);
        getTest().info("კატეგორიის ფილტრი ");
        Response response = apiManager.getParams("/products", apiManager.getHeaders(), params);
        List<Map<String, Object>> products = response.jsonPath().getList("");
        apiManager.assertWithLog(response.statusCode(), 200," შედარება ");
        Assert.assertFalse(products.isEmpty(), "სია ცარიელია");
        getTest().info("შედეგი — " + products.size() + " პროდუქტი");
        products.forEach(p -> {
            Map<String, Object> category = (Map<String, Object>) p.get("category");
            apiManager.assertWithLog((Integer) category.get("id"), 1,"შედარება");
        });
    }












}