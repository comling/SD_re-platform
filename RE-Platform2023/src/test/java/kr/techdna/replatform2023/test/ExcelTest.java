package kr.techdna.replatform2023.test;

import kr.techdna.replatform2023.service.BusinessDataService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.util.ZipEntrySource;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ExcelTest {

    @Autowired
    BusinessDataService businessDataService;

    @Test
    public void excelTest() throws InvalidFormatException {
//        Map<String, Object> data = businessDataService.getBusinessDataList();
        ClassPathResource classPathResource = new ClassPathResource("static/templates/excel-template.xlsx");
        System.out.println(classPathResource);
        try (InputStream is = new BufferedInputStream(classPathResource.getInputStream())) {
            OPCPackage opcPackage = OPCPackage.open(is);
            System.out.println(opcPackage);
            XSSFWorkbook wb = new XSSFWorkbook(opcPackage);
            String sheetName = wb.getSheetName(0);
            System.out.println(sheetName);
            Sheet sheet = wb.getSheet(sheetName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
