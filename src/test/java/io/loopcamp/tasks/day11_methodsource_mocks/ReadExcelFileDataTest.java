package io.loopcamp.tasks.day11_methodsource_mocks;

import org.junit.jupiter.api.Test;
import utilities.ExcelUtil;

import java.util.List;
import java.util.Map;

public class ReadExcelFileDataTest {

    @Test
    public void readDocBetaUsersTest(){
        String filePath = "src/test/resources/DocBeta.xlsx";

        ExcelUtil excelfile = new ExcelUtil(filePath, "BETA2");
        System.out.println("Column Names: " + excelfile.getColumnsNames());

        int rowCount = excelfile.rowCount();
        System.out.println("row count: " + rowCount);

        List<Map<String, String>> data = excelfile.getDataList();
        System.out.println("data: " + data);
    }
}
