package org.example.page_objects;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.utilities.XLSXFileHandelerHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class StockListDailyLosersPage {

    public final int RECORD_LIMIT          = 10;  /** limit data fetch records as 2000+ records are present */
    public final String XPATH_LOSERS_TABLE = "//div[@id='leftcontainer']//table[contains(@class,'dataTable')]";
    public final String  XLSX_FILE_PATH    = System.getProperty("user.dir") + "/src/test/data/StockMarketTopDailyLoserRecords.xlsx";

    public String[] headerRowsNamesArray;
    public List<WebElement> listWebElementTRs;

    public WebDriver driver;

    public StockListDailyLosersPage(WebDriver driver) {
        this.driver = driver;
    }


    public void readStockListingDataFromTable() throws IOException {

        this.headerRowsNamesArray = this.getHeaderRowNamesStringArray( XPATH_LOSERS_TABLE );
        this.listWebElementTRs    = this.getListWebElemTableRows();

    }

    public void writeDataToXLSXFile() throws IOException {

        this.writeStockDataToExcel( listWebElementTRs , headerRowsNamesArray );
    }


    public List<WebElement> getListWebElemTableRows() {

        if( this.listWebElementTRs == null ) {
            this.listWebElementTRs = driver.findElements( By.xpath("//div[@id='leftcontainer']//table[contains(@class,'dataTable')]/tbody/tr" ) )
                    .subList( 0, RECORD_LIMIT );
        }
        return this.listWebElementTRs;

    }


    private String[] getHeaderRowNamesStringArray(String tableXpath) {

        tableXpath += "/thead/tr/th";
        List<WebElement> listWebElemTH = driver.findElements( By.xpath(tableXpath) );
        String tHnamesArray[] = listWebElemTH.stream().map( (webElemTH) -> ( webElemTH.getText()  ) ).toArray( size -> new String[size]);
        return tHnamesArray;
    }

    /**
     * @desc
     * Create New/ Delete-Overwrite New XLSX File -> write all table header and data to XLSX file
     */
    private void writeStockDataToExcel(List<WebElement> listWebElemTableRows, String[] headerRowsNamesArray) throws IOException {


        final String sheetName = "Sheet1";

        XLSXFileHandelerHelper xlsxFileHandelerHelper = new XLSXFileHandelerHelper();
        xlsxFileHandelerHelper.createOverwriteNewFile( XLSX_FILE_PATH , sheetName );

        //Write all data to file process.
        FileInputStream inputStream = new FileInputStream( XLSX_FILE_PATH );
        Workbook workbook = new XSSFWorkbook( inputStream );
        Sheet sheet = workbook.getSheetAt(0);

        //Write Header Row
        Row headerRow = sheet.createRow(0 );
        int cellCounter = 0;
        for( String headerRowItem : headerRowsNamesArray ) {
            headerRow.createCell(cellCounter ).setCellValue( headerRowItem );
            cellCounter++;
        }

        // Write data rows
        int rowCounter = 1;
        Row row;
        for ( WebElement webElemTr : listWebElemTableRows ) {
            //Company	  Group	   Prev Close (Rs)	Current Price (Rs)	% Change
            //Unistar Multimedia	X	8.24	7.30	-11.41
            String company       = webElemTr.findElement( By.xpath( "./td[1]" ) ).getText();
            String group         = webElemTr.findElement( By.xpath( "./td[2]" ) ).getText();
            float prevClose      = Float.parseFloat( webElemTr.findElement( By.xpath( "./td[3]" ) ).getText() );
            float currentPrice   =  Float.parseFloat( webElemTr.findElement( By.xpath( "./td[4]" ) ).getText() );
            float percentChange  =  Float.parseFloat( webElemTr.findElement( By.xpath( "./td[5]" ) ).getText() );

            row = sheet.createRow( rowCounter );
            row.createCell(0 ).setCellValue( company );
            row.createCell(1 ).setCellValue( group );
            row.createCell(2 ).setCellValue( String.format("%.2f", prevClose )  );
            row.createCell(3 ).setCellValue( String.format("%.2f", currentPrice ) );
            row.createCell(4 ).setCellValue( String.format("%.2f", percentChange ) );

            rowCounter++;
        }

        inputStream.close();

        // Write/Save to the file
        FileOutputStream outputStream = new FileOutputStream( XLSX_FILE_PATH );
        workbook.write(outputStream);
        outputStream.close();


    }

}
