# MyMarket.ge — Test Automation Framework

#### ტესტირების აცტომატიზაციის ინჟინერი: გიორგი მიქელაძე  
#### გარემო: QA  
## ტექნოლოგიები: *Java,* *Selenium*, *TestNG,* *RestAssured*, *ExtentReports.*



## კონფიგურაციები


### Login

#### login.mail=your@email.com
#### login.password=yourpassword
#### login.url=https://www.mymarket.ge/ka/

# URLs

#### sell.url=https://www.mymarket.ge/ka/pr-form/?AdType=1
#### buy.url=https://www.mymarket.ge/ka/pr-form/?AdType=2
#### rent.url=https://www.mymarket.ge/ka/pr-form/?AdType=3
#### service.url=https://www.mymarket.ge/ka/pr-form/?AdType=4
#### visit.url=https://www.mymarket.ge/ka/

## User
### user.id=ID 9060160

## Wait
#### wait=10

# API Defaults
UNIQ.TITLE=product
DEF.PRICE=25
DEF.DESCRIPTION=test description
DEF.CATID=1
DEF.IMAGE=https://placehold.co/600x400
DEF.TITLE=test-product


##  *ტესტების აღწერა* 
###  LoginTest ტესტები უმეტესწილად არის სადემონსტრაციოდ!
#### თუ როგორ გადაიღებს სქრინშოთს შეცდომებზე. (ტესტები არასრულყოფილია)
### LoginTest
#### `testValidLogin` – ვალიდური ავტორიზაცია + refresh შემოწმება 
#### `testEmptyMailLogin` – ცარიელი მეილით შესვლა 
#### `testEmptyPassLogin` – ცარიელი პაროლით შესვლა 
#### `testEmptyLogin` – ორივე ველი ცარიელია 
#### `testRefreshSendKeyLogin` – URL დარეფრეშების შემდეგ 
#### `navigationLogin` – ნავიგაცია + URL შემოწმება 
#### `testMailSymbolsLogin` – `@gmail.com` სიმბოლოებით შესვლა 
#### `testLogout` – გამოსვლა 

---

### AdvertisementTest
#### `Advertisementcheck` – Username ვალიდაცია                           
#### `CheckSellBtn` – გაყიდვის ღილაკი                              
#### `chechBuyBtn` – ყიდვის ღილაკი                                
#### `checkRentBtn` – გაქირავების ღილაკი                           
#### `checkServiceBtn` – სერვისების ღილაკი                            
#### `CheckSellCategory` – გაყიდვის კატეგორიები                         
#### `CheckSellTypeAllItems` – ყველა კატეგორიის navigation                  
#### `CheckSellCategoryData` – კატეგორიების JSON-თან შედარება               
#### `checkSelltypeCategoryDataBrands` – ბრენდების JSON-თან შედარება                  
#### `checkBuyCategories` – ყიდვის კატეგორიები                           
#### `checkBuyTypeCategoryDataBrands` – ყიდვის ტიპი, კატეგორია და ბრენდები           
#### `checkRentCategories` – გაქირავების ტიპის კატეგორიები                
#### `CheckRentTypeCategoryData` – გაქირავების მონაცემების შედარება (ჯეისონთან) 
#### `CheckServiceCategories` – სერვისების კატეგორიები                       
#### `CheckServiceTypeCategoryData` – სერვისების მონაცემები                        


###  ApiTest
#### `getProductE2ETest` – სრული E2E ციკლი 
#### `getProductById` –  ID-ით მიღება 
#### `getProductsWithQuery` – pagination limit=5 
#### `createProduct` – პროდუქტის შექმნა 
#### `createWithoutPrice` – ფასის გარეშე 
#### `deleteProduct` – წაშლა + დადასტურება 
#### `createAndGetE2E` –  შექმნა და წაკითხვა 
#### `createDeleteE2E` –  შექმნა და წაშლა 
#### `filterByPriceRange` –  ფასის ფილტრი 
#### `filterByCategoryId` –  კატეგორიის ფილტრი 





## 📸 სქრინშოთები

#### FAIL ტესტების სქრინშოთები ავტომატურად ინახება:

```
screenshots/{testName}.png
```

> **შენიშვნა:** `groups = "no-screenshot"` annotation-იანი ტესტები სქრინშოთს არ იღებენ — ეს გამოიყენება API ტესტებისთვის.

---

## 📄 JSON მონაცემები
კატეგორიების ვალიდაციისთვის გამოიყენება:
src/test/category.Json


## დოკუმენტზე მუშაობდა

*გიორგი მიქელაძე*
*QA Automation Engineer*

გარემო: QA | პლატფორმა: mymarket.ge
