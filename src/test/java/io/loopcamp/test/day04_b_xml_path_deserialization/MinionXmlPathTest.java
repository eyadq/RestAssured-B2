package io.loopcamp.test.day04_b_xml_path_deserialization;

import utilities.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionXmlPathTest extends MinionTestBase {


    /**
     Given accept type is application/xml
     When I send GET request to /minions
     -------------
     Then status code is 200
     And content type is XML
     And minion at index 0 is matching: (NOTE: Your index 0 might be different)
     id > 1
     name>Meade
     gender >Male
     phone >9994128233
     */
    @DisplayName("Get /minions -- with XML")
    @Test
    public void minionXmlPathTest(){
        Response response =
                given().accept(ContentType.XML)
                .when().get("/minions");

        //response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.XML.toString(), response.contentType());

        //Convert XML response body to XmlPath object
        XmlPath xmlPath = response.xmlPath();
        System.out.println("id:" + xmlPath.getInt("List.item[0].id"));
        System.out.println("name: " + xmlPath.getString("List.item[0].name"));
        System.out.println("gender: " + xmlPath.getString("List.item[0].gender"));
        System.out.println("phone: " + xmlPath.getLong("List.item[0].phone"));
        //System.out.println("phone: " + xmlPath.getString("List.item[0].phone")); //as works

        assertEquals(1, xmlPath.getInt("List.item[0].id"));
        assertEquals("Meade", xmlPath.getString("List.item[0].name"));
        assertEquals("Male", xmlPath.getString("List.item[0].gender"));
        assertEquals(9994128233l, xmlPath.getLong("List.item[0].phone"));

        //Can you get me the names
        List<String> names = xmlPath.getList("List.item.name");
        System.out.println("All minion names: " + names);


    }

}
