package io.loopcamp.tasks.day05_tasks;

import io.loopcamp.ZipInfoPathCity;
import io.loopcamp.pojo.ZipInfo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Zipcode {

    @BeforeAll
    public static void setBaseURL(){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @Test
    public void getZip(){
        String countryNameExpected = "United Sates";
        String cityNameExpected = "Fairfax";
        String stateNameExpected= "Virginia";
        String latitudeExpected= "38.8604";
        String headerServerExpected = "cloudflare";
        boolean headerReportToExistsExpected = true;

        String targetCountryCode = "us";
        int targetZipcode = 22031;

        Response response =
            given().accept(ContentType.JSON)
            .and().pathParam("country", targetCountryCode)
            .and().pathParam("zipcode", targetZipcode)
            .when().get("/{country}/{zipcode}");

        ZipInfo zipInfo = response.body().as(ZipInfo.class);
        zipInfo.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals(headerServerExpected, response.header("Server"));
        assertEquals(headerReportToExistsExpected, response.header("Report-To") != null);
        assertEquals("" + targetZipcode, zipInfo.getPostCode());
        assertEquals(targetCountryCode.toUpperCase(), zipInfo.getCountryAbbreviation());
        assertEquals(cityNameExpected, zipInfo.getPlaces().get(0).getPlaceName());
        assertEquals(stateNameExpected, zipInfo.getPlaces().get(0).getState());
        assertEquals(latitudeExpected, zipInfo.getPlaces().get(0).getLatitude());

    }

    @Test
    public void zipcodeNotExist(){
        String targetCountryCode = "us";
        int targetZipcode = 50000;

        Response response =
                given().accept(ContentType.JSON)
                        .and().pathParam("country", targetCountryCode)
                        .and().pathParam("zipcode", targetZipcode)
                        .when().get("/{country}/{zipcode}");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    public void getZipWithCity(){
        String countryNameExpected = "United States";
        String cityNameExpected = "Fairfax";
        String stateNameExpected= "Virginia";
        String latitudeExpected= "38.8604";
        String headerServerExpected = "cloudflare";
        boolean headerReportToExistsExpected = true;

        String targetCountryCode = "us";
        String targetStateAbbreviation = "va";
        String targetCity = "fairfax";
        int targetZipcode = 22031;

        Response response =
                given().accept(ContentType.JSON)
                        .and().pathParam("country", targetCountryCode)
                        .and().pathParam("state", targetStateAbbreviation)
                        .and().pathParam("city", targetCity)
                        .when().get("/{country}/{state}/{city}");

        response.prettyPrint();

        ZipInfoPathCity zipInfoPathCity = response.body().as(ZipInfoPathCity.class);
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals(targetCountryCode.toUpperCase(), zipInfoPathCity.getCountryAbbreviation());
        assertEquals(countryNameExpected, zipInfoPathCity.getCountry());
        assertEquals(cityNameExpected, zipInfoPathCity.getPlaceName());
        for (ZipInfoPathCity.Place place : zipInfoPathCity.getPlaces()){
            String placeName = place.getPlaceName();
            String zipCode = place.getPostCode();
            assertTrue(placeName.contains("Fairfax"));
            assertEquals("22", zipCode.substring(0, 2));
        }

    }


}
