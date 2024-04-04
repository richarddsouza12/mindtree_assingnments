package org.example.tests;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.page_objects.StockListDailyLosersPage;
import org.example.test_components.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockMarketExampleTest extends BaseTest {


    public final String WEBSITE = "https://money.rediff.com/losers/bse/daily/grouall";
    public final String XLSX_FILE_PATH = "/src/test/data/StockMarketTopDailyLoserRecords.xlsx";

    @Test
    public void testStockMarketLoserListHashmapXlsExample() throws IOException {
        
        /**
         * STEPS
         *  1 - Read Data Form Stock Market Site with Selenium and Create/Write to new XLSX file.
         *  2 - Read the newly created XLS file and populate List<Hashmap> listHashMap1
         *  3 - Populate List<Hashmap> listHashMap2 from Selenium WebElements List data
         *  4 - Compare both the hashmaps using SoftAsserts
         * */
        
        super.initializeWebDriver();
        this.driver.get( WEBSITE );

        //1 - Read Data Form Stock Market Site with Selenium and Create/Write to new XLSX file.
        StockListDailyLosersPage stockListDailyLosersPage = new StockListDailyLosersPage( driver );
        stockListDailyLosersPage.readStockListingDataFromTable();
        stockListDailyLosersPage.writeDataToXLSXFile();


        //2-Read the newly created XLS file and populate List<Hashmap> listHashMap1
        ArrayList<HashMap<String, Object>> listHashMaps1 = this.getReadXLSXFilePopulateHashMapList1();

        //3-Populate Hashmap2 from Selenium WebElements List data
        ArrayList<HashMap<String,Object>> listHashMaps2 = this.populateHashMapList2FromTableWebElementsList( stockListDailyLosersPage.getListWebElemTableRows() );


        //4 -Compare both the hashmaps
        consolePrettyPrintHashMaps(listHashMaps1, listHashMaps2);

        SoftAssert softAssert = new SoftAssert();
        HashMap<String,Object> HashMapTempSet1;
        HashMap<String,Object> HashMapTempSet2;

        for( int i = 0 ; i < listHashMaps1.size() ; i++ ) {

            HashMapTempSet1 = listHashMaps1.get(i);
            HashMapTempSet2 = listHashMaps2.get(i);

            softAssert.assertEquals( HashMapTempSet1.get("company"),           HashMapTempSet2.get("company") );
            softAssert.assertEquals( HashMapTempSet1.get("group"),             HashMapTempSet2.get("group") );
            softAssert.assertEquals( HashMapTempSet1.get("prev_close"),        HashMapTempSet2.get("prev_close") );
            softAssert.assertEquals( HashMapTempSet1.get("current_price"),     HashMapTempSet2.get("current_price") );
            softAssert.assertEquals( HashMapTempSet1.get("change_percentage"), HashMapTempSet2.get("change_percentage") );
            //***OR***
            //softAssert.assertEquals( HashMapTempSet1, HashMapTempSet2 );
        }

        softAssert.assertAll();
    }

    private void consolePrettyPrintHashMaps(ArrayList<HashMap<String, Object>> listHashMaps1, ArrayList<HashMap<String, Object>> listHashMaps2) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println( "listHashMaps1" );
        System.out.println( gson.toJson(listHashMaps1) );

        System.out.println( "listHashMaps2" );
        System.out.println( gson.toJson(listHashMaps2) );

    }

    private ArrayList<HashMap<String, Object>> populateHashMapList2FromTableWebElementsList( List<WebElement> listWebElemTableRows ) {

        ArrayList<HashMap<String,Object>> listHashMaps2 = new ArrayList<HashMap<String,Object>>();

        for ( WebElement webElemTr : listWebElemTableRows ) {

            String company       = webElemTr.findElement( By.xpath( "./td[1]" ) ).getText();
            String group         = webElemTr.findElement( By.xpath( "./td[2]" ) ).getText();
            float prevClose      = Float.parseFloat( webElemTr.findElement( By.xpath( "./td[3]" ) ).getText() );
            float currentPrice   =  Float.parseFloat( webElemTr.findElement( By.xpath( "./td[4]" ) ).getText() );
            float percentChange  =  Float.parseFloat( webElemTr.findElement( By.xpath( "./td[5]" ) ).getText() );

            HashMap<String,Object> hashMap = new HashMap<String,Object>();
            hashMap.put( "company", company );
            hashMap.put( "group", group );
            hashMap.put( "prev_close", prevClose );
            hashMap.put( "current_price", currentPrice );
            hashMap.put( "change_percentage", percentChange );

            listHashMaps2.add( hashMap );
        }
        return listHashMaps2;
    }

    private ArrayList<HashMap<String,Object>> getReadXLSXFilePopulateHashMapList1() throws IOException {
        /**
         * HM {
         *     company :
         *     group :
         *     prev_close :
         *     current_price :
         *     change_percentage :
         *  }
         **/
        FileInputStream inputStream = new FileInputStream( System.getProperty("user.dir") + XLSX_FILE_PATH );
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        ArrayList<HashMap<String,Object>> listHashMaps1 = new ArrayList<HashMap<String,Object>>();

        for ( int row_counter = 1 ; row_counter < rows ; row_counter++ ) {

            Row row = sheet.getRow( row_counter );
            HashMap<String,Object> hashMap = new HashMap<String,Object>();

            hashMap.put( "company", row.getCell(0).getStringCellValue() );
            hashMap.put( "group", row.getCell(1).getStringCellValue() );
            hashMap.put( "prev_close",  Float.parseFloat( row.getCell(2).getStringCellValue() ) );
            hashMap.put( "current_price", Float.parseFloat( row.getCell(3).getStringCellValue() ) );
            hashMap.put( "change_percentage", Float.parseFloat(  row.getCell(4).getStringCellValue() ) );

            listHashMaps1.add( hashMap );

        }
        return listHashMaps1;
    }


}
