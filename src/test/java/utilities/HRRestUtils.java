package utilities;

import io.loopcamp.pojo.Region;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class HRRestUtils extends HRTestBase {

    public static Map<String, Object> createNewRegion(int region_id, String region_name){
        return Map.ofEntries(Map.entry("region_id", region_id), Map.entry("region_name", region_name));
    }

    public static Response postNewRegion(int region_id, String region_name){
        return given().accept(ContentType.JSON)
                        .and().contentType(ContentType.JSON)
                        .and().body(
                                createNewRegion(region_id, region_name)
                                )
                        .when().post("/regions/");
    }

    public static Response postNewRegionPojo(int region_id, String region_name){
        Region region = new Region();
        region.setRegionId(100);
        region.setRegionName("Test Region");
//      region.setLinks(links);

        return given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(region)
                .when().post("/regions/");
    }

    public static Response putRegion(int regionId, String newRegionName){
        return given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("region_id", regionId)
                .and().body(HRRestUtils.createNewRegion(regionId, newRegionName))
                .when().put("/regions/{region_id}");
    }

    public static void deleteRegion(int regionId){
        System.out.println("Deleting Region " + regionId);
        given().accept(ContentType.JSON)
                .and().pathParam("region_id", regionId)
                .when().delete("/regions/{region_id}");
                //.then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    public static Map<String, Object> getRegionAsMap(int regionId){
        Response getResponse = given().accept(ContentType.JSON)
                .and().pathParam("region_id", regionId)
                .when().get("/regions/{region_id}");

        return getResponse.body().as(Map.class);
    }

    public static Map<String, Object> obtainRegionAsMapFromDB(int regionId){
        String query = "SELECT * from regions where REGION_ID = " + regionId;

        String dbUrl = ConfigurationReader.getProperty("hr.db.url");
        String username = ConfigurationReader.getProperty("hr.db.username");
        String password = ConfigurationReader.getProperty("hr.db.password");
        DBUtils.createConnection(dbUrl, username, password);

        Map<String, Object> map = DBUtils.getRowMap(query);
        DBUtils.destroy();

        return map;
    }
}
