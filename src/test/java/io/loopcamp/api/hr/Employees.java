package io.loopcamp.api.hr;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Employees extends HRApiTestBase{


    @Test
    public void getAllEmployees(){
        int employeeId = 1;

        int count = given()
                .headers(getAcceptHeader())
                .queryParams("limit", 1000)
                .when()
                .get("/employees")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("count");


        DBUtils.createConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        int countFromDB = 0;
        try {
            countFromDB = DBUtils.getRowCount("select * from employees");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(countFromDB, count);
    }


    @Test
    public void getSingleEmployee(){
        int employeeId = 100;

        Map<String, Object> employee =
                given()
                        .headers(getAcceptHeader())
                        .when()
                        .get("/employees/{employee_id}", employeeId)
                        .then().extract().body().as(Map.class);

        System.out.println(employee);

    }
}
