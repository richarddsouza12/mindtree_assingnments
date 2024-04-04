# Mindtree Assingnments
- Assingnment1 - FlipkartLinks
- Assingnment 2 - Karate Project
- Assingnment 3 - Selenium Hashmap Excell Assingnment

# Assingnment1 - FlipkartLinks
MindTree Assingnment 1 - Links on Flipkart Url


# Assingnment 2 - Karate Project
Herokuapp Booking APIs tests using Karate Java

**Contents**
- API tests with BDD feature files
- constants in karate-config.js
- Reports generate by default in target/karate-reports
- Covered : Called once get auth token , list bookings , create booking , update put , patch , delete

**Command**
```
mvn test -Dtest=RestfulBookingRunner
OR
mvn test     

```

# Assingnment 3 - Selenium Hashmap Excell Assingnment
StockMarket Listing Selenium Excell Hasmaps

**Contents**
- Page Object Model Approach
- TestNG , Maven , Maven Surefire Plugin , testng.xml file
- Limit records fetched due to 2000+ records. Config in POM file

**Command**
```
 mvn test     OR
 mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

**Test Case Steps**
- 1 - Read Data Form Stock Market Site with Selenium and Create/Write to new XLSX file.
- 2 - Read the newly created XLS file and populate List<Hashmap> listHashMap1
- 3 - Populate List<Hashmap> listHashMap2 from Selenium WebElements List data
- 4 - Compare both the hashmaps using SoftAsserts


**Screencast of execution in Root Folder**

/Screencast Execution.webm