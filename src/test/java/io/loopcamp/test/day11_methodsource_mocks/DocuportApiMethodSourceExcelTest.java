package io.loopcamp.test.day11_methodsource_mocks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utilities.DocuportApiTestBase;
import utilities.ExcelUtil;

import java.util.List;
import java.util.Map;

public class DocuportApiMethodSourceExcelTest extends DocuportApiTestBase {

    public static List<Map<String, String>> getUserCredentials(){
        String filePath = "src/test/resources/DocBeta.xlsx";
        ExcelUtil excelfile = new ExcelUtil(filePath, "BETA2");
        return excelfile.getDataList();
    }

    @ParameterizedTest
    @MethodSource("getUserCredentials")
    public void docuportCredentialsTest(Map<String, String> credentials){
        System.out.println(credentials);
        System.out.println(credentials.get("email") + " token: " + getAccessToken(credentials.get("email")));
    }
}

