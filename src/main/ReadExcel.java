package main;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by bumskim on 2017. 6. 7..
 */
public class ReadExcel {
    static public void main(String[] args) {
        doRead();
    }

    static private void doRead(){
        // XSSFWorkbook, InputStream, needs more memory

        FileInputStream myInputStream = null;
        try {
            myInputStream = new FileInputStream(new File("src/excel/test.xlsx"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        OPCPackage pkg = null;
        try {
            pkg = OPCPackage.open(myInputStream);
        } catch(Exception e) {
            e.printStackTrace();
        }

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(pkg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sh = wb.getSheetAt(0);

        for(Row row : sh) {
            for(Cell cell : row) {
                System.out.print(cell.getStringCellValue());
            }
            System.out.println();
        }


        try {
            pkg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
