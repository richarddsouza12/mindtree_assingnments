package org.example.utilities;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLSXFileHandelerHelper {

    /**
     * @desc Create new XLSX file if not present, Delete and create if present.
     */
    public void createOverwriteNewFile(String filePath, String sheetName) {

        // Create the file object
        File file = new File( filePath );
        FileOutputStream outputStream;

        // Check if the file already exists and delete it
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
                return;
            }
        }

            Workbook workbook = new XSSFWorkbook();
            // Create a blank sheet
            Sheet sheet = workbook.createSheet(sheetName);

            // Write the workbook to a file
            try {
                outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
                System.out.println("Excel file created successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
