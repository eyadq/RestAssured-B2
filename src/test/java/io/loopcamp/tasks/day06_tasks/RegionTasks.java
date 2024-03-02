package io.loopcamp.tasks.day06_tasks;

import io.loopcamp.pojo.Region;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;
import utilities.HRRestUtils;
import utilities.HRTestBase;

import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RegionTasks extends HRTestBase {
    int regionId = 100;
    String regionName = "Test Region";
    String newRegionName = "Puzzle Region";

    @Test
    public void getRegionAsPojo(){
        Response getResponse = given().accept(ContentType.JSON)
                .and().pathParam("region_id", 50)
                .when().get("/regions/{region_id}");

        Region region = getResponse.body().as(Region.class);
        System.out.println(region);


    }

    /**
     * given accept is json
     * and content type is json
     * When I send post request to "/regions/"
     * With json:
     * {
     *     "region_id":100,
     *     "region_name":"Test Region"
     * }
     * Then status code is 201
     * content type is json
     * region_id is 100
     * region_name is Test Region
     */

    @Test
    public void postPutGetDBDeleteRegionName(){
        //Post new Region using Map (could also use Pojo)
        Response postResponse = HRRestUtils.postNewRegion(regionId, regionName);
        JsonPath jsonPath = postResponse.jsonPath();
        assertThat(postResponse.statusCode(), is(HttpStatus.SC_CREATED));
        assertThat(jsonPath.getInt("region_id"), is(regionId));
        assertThat(jsonPath.getString("region_name"), is(regionName));

        //Put - update new Region using Map (could also use Pojo)
        Response putResponse = HRRestUtils.putRegion(regionId, newRegionName);
        JsonPath putJasonPath = putResponse.jsonPath();
        assertThat(putResponse.statusCode(), is(HttpStatus.SC_OK));
        Map<String, Object> regionMap = HRRestUtils.getRegionAsMap(regionId);
        assertThat(regionMap.get("region_id"), is(regionId));
        assertThat(regionMap.get("region_name"), is(newRegionName));

        //Obtain new region using the databse
        Map<String, Object> regionFromDB = HRRestUtils.obtainRegionAsMapFromDB(regionId);
        assertThat(regionFromDB.get("REGION_NAME"), is(regionMap.get("region_name")));
        HRRestUtils.deleteRegion(regionId);
    }

    @Test
    public void deleteRegionTest(){
        HRRestUtils.deleteRegion(100);
    }
}
