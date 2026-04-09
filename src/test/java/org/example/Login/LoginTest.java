package org.example.Login;
import org.example.BaseTest;
import org.example.pages.LoginPage;
import org.example.utils.AssertHelperManager;
import org.example.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.example.utils.ExtentReportManager.getTest;

@Listeners(value = org.example.utils.TestListener.class)
public class LoginTest extends BaseTest {


    @Test
    public void testValidLogin()  {
    LoginPage loginPage = new LoginPage(driver);
        getTest().info(" ვალიდური ავტორიზაცია");
        getTest().info("მონაცემების შეყვანა: " + ConfigReader.get("login.mail"));
        loginPage.Login(ConfigReader.get("login.mail"), ConfigReader.get("login.password"));
        getTest().info("ავტორიზაციის შემოწმება");
     assertstring(loginPage.logincheck(),ConfigReader.get("login.mail"));
     Assert.assertTrue(loginPage.useridckeck().contains("9060160"));
        getTest().info("გვერდის დარეფრეშება");
     loginPage.refreshPage();
        getTest().info("ავტორიზაციის სტატუსის ხელახალი შემოწმება დარეფრეშების შემდეგ");
     assertstring(loginPage.logincheck(),ConfigReader.get("login.mail"));
     Assert.assertTrue(loginPage.useridckeck().contains("9060160"));

}

    @Test
    public void testEmptyMailLogin()  {
        getTest().info("ავტორიზაცია ცარიელი მეილით");
        LoginPage loginPage = new LoginPage(driver);
        getTest().info("მონაცემების შეყვანა (ცარიელი მეილი, ვალიდური პაროლი)");
        loginPage.Login("         ", ConfigReader.get("login.password"));
        getTest().info("შეცდომის შეტყობინების ვალიდაცია");
        assertstring(loginPage.getcurrentURL(),"https://auth.tnet.ge/ka/user/login?Continue=https%3A%2F%2Fwww.mymarket.ge%2Fka%2F");
       assertstring(loginPage.emptymaillogin(),"სავალდებულო ველი");
    }

    @Test
    public void testEmptyPassLogin()  {
        getTest().info(" ავტორიზაცია ცარიელი პაროლით");
        LoginPage loginPage = new LoginPage(driver);
        getTest().info("მონაცემების შეყვანა (ვალიდური მეილი, ცარიელი პაროლი)");
        loginPage.Login(ConfigReader.get("login.mail"), "      ");
        getTest().info("URL-ის და შეცდომის შეტყობინების ვალიდაცია");
        assertstring(loginPage.getcurrentURL(),"https://auth.tnet.ge/ka/user/login?Continue=https%3A%2F%2Fwww.mymarket.ge%2Fka%2F");
        assertstring(loginPage.emptypasslogin(),"სახელი ან პაროლი არასწორია");
    }

    @Test
    public void testEmptyLogin() {
        //visit
        LoginPage loginPage = new LoginPage(driver);
        getTest().info(" ცარიელი ველებით ავტორიზაცია");
        getTest().info("ავტორიზაციის მცდელობა მონაცემების გარეშე");
        loginPage.Login("", "");
        getTest().info("ვალიდაციის შეტყობინებების შემოწმება");
        assertstring(loginPage.getcurrentURL(), "https://auth.tnet.ge/ka/user/login?Continue=https%3A%2F%2Fwww.mymarket.ge%2Fka%2F");
        assertstring(loginPage.emptypassvalid(), "სავალდებულო ველი");
        assertstring(loginPage.emptymaillogin(), "სავალდებულო ველი");
    }

    @Test
    public void testRefreshSendKeyLogin()  {
        getTest().info("URL-ის შენარჩუნება დარეფრეშებისას");
        LoginPage loginPage = new LoginPage(driver);
        getTest().info("ავტორიზაცია");
        loginPage.Login(ConfigReader.get("login.mail"), ConfigReader.get("login.password"));
        assertstring(loginPage.getcurrentURL(),ConfigReader.get("login.url"));
        getTest().info("გვერდის რეფრეში");
        loginPage.refreshPage();
        getTest().info("URL-ის შემოწმება რეფრეშის შემდეგ");
        assertstring(loginPage.getcurrentURL(), ConfigReader.get("login.url"));
    }

    @Test
    public void navigationLogin()  {
        getTest().info("ნავიგაცია და დარეფრეშება ავტორიზაციის შემდეგ");
    LoginPage loginPage = new LoginPage(driver);
        SoftAssert softAssert = new SoftAssert();
        AssertHelperManager assertHelperManager = new AssertHelperManager(driver);
        getTest().info("ავტორიზაცია");
        loginPage.Login(ConfigReader.get("login.mail"), ConfigReader.get("login.password"));
        getTest().info("გადასვლა განცხადების გვერდზე ");
        assertHelperManager.navigateToAdvertisementPage(softAssert);
        getTest().info("URL-ის შემოწმება");
    assertstring(loginPage.getcurrentURL(), ConfigReader.get("login.url"));
        getTest().info("გვერდის რეფრეში და URL-ის ხელახალი შემოწმება");
    loginPage.refreshPage();
    assertstring(loginPage.getcurrentURL(), ConfigReader.get("login.url"));
softAssert.assertAll();
}

    @Test
    public void testMailSymbolsLogin() {
        getTest().info(" @gmail.com სიმბოლოებით დალოგინება ");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.Login("@gmail.com", ConfigReader.get("login.password"));
        getTest().info("დალოგინება");
        assertstring(loginPage.getcurrentURL(), ConfigReader.get("login.url"));
        assertstring(loginPage.onlymailvalid(), "გთხოვთ, შეიყვანეთ ელფოსტის სწორი მისამართი");
getTest().info("დალოგინება წარმატებულია! ");

    }

    @Test
    public void testLogout()  {
        getTest().info(" დალოგაუთება ");
        LoginPage loginPage = new LoginPage(driver);
        getTest().info(" დალოგინება ");
        loginPage.Login(ConfigReader.get("login.mail"), ConfigReader.get("login.password"));
        getTest().info(" შემოწმება ");
        assertstring(loginPage.logincheck(), ConfigReader.get("login.mail"));
        Assert.assertTrue(loginPage.useridckeck().equals("user.id"));
        getTest().info(" დარეფრეშება ");
        loginPage.refreshPage();
        getTest().info(" დარეფრეშება ");
        assertstring(loginPage.logincheck(), ConfigReader.get("login.mail"));
        loginPage.clickLogoutbtn();
      assertstring(loginPage.getcurrentURL(),ConfigReader.get("visit.url"));



    }



}
