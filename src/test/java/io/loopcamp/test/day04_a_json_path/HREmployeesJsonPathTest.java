package io.loopcamp.test.day04_a_json_path;

import utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class HREmployeesJsonPathTest extends HRTestBase {

    /**
     Given accept type is Json
     And query param limit is 2
     when I send GET request to /employees
     Then I can use jsonPath to query and read values from json body
     */
    @DisplayName("GET /employees?limit=2")
    @Test
    public void jsonPathEmployeeTest(){
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("limit", 20);
        //queryMap.put("offset", 2);

        Response response =
                given().accept(ContentType.JSON)
                        .and().queryParams(queryMap)
                        .when().get("/employees");

//        Response response =
//                given().accept(ContentType.JSON)
//                        .and().queryParam("limit", 2)
//                        .when().get("/employees");

        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        System.out.println("1st employee name: " + jsonPath.getString("items[0].first_name"));
        System.out.println("1st employee name: " + jsonPath.getString("items[0].first_name"));

        List<String> emails = jsonPath.getList("items.email");
        System.out.println("Emails = " + emails);

        // assert that NYANG email is among all options
        assertTrue(emails.contains("NYANG"));


        // get all employees first name who work for department_id=90
        /*
            SELECT first_name FROM employees
            WHERE department_id = 90;
         */

        // findAll() --- >  comes from scripting groovy language (based on java)
        List<String> firstNames = jsonPath.getList("items.first_name");
        System.out.println("First Names: " + firstNames);
        System.out.println(firstNames.size());

        // for those who are under department_id 90
        List <String> firstNamesWithDepId90 = jsonPath.getList("items.findAll{it.department_id==90}.first_name");
        System.out.println("First Names for those with Dep_ID 90: " + firstNamesWithDepId90);

        // find all first_name for whose who works as IT_PROG
        List <String> firstNamesWithIt_Prog = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name");
        System.out.println("First Names for those with Dep_ID 90: " + firstNamesWithIt_Prog);


        // find All first name who is making more than 5000
        List <String> allNameForSalaryMoreThan5000 = jsonPath.getList("items.findAll{it.salary > 5_000}.first_name");
        System.out.println("All Names For Salary More Than 5000: " + allNameForSalaryMoreThan5000);

        // find first name for the max salary -- > max{}
        String maxSalaryName = jsonPath.getString("items.max{it.salary}.first_name");
        System.out.println("Max Salary Name = " + maxSalaryName);


        // find first name for the mix salary -- > max{}
        String minSalaryName = jsonPath.getString("items.min{it.salary}.first_name");
        System.out.println("Min Salary Name = " + minSalaryName);


        // what is the min salary with related infos
        String minSalary = jsonPath.getString("items.min{it.salary}");
        System.out.println("Min Salary: " + minSalary);
}


}
