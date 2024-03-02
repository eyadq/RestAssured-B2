package utilities;

import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.datafaker.Faker;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class MinionSecureRestUtils extends MinionSecureTestBase{

    private static String baseUrl = ConfigurationReader.getProperty("minion.api.url");

    public static void deleteMinionById (int id) {

        System.out.println("DELETING minion with id {" + id + "}");
        given().accept(ContentType.JSON)
                .and().pathParam("id", id)
                .when().delete("/minions/{id}");
        //.then().log().all();
    }

    /**
     * This method returns new Minion with randomly generated data
     * @return Minion
     */
    public static Minion getNewMinion(){
        Minion minion = new Minion();
        Faker faker = new Faker();
        minion.setName(faker.name().firstName());
        minion.setPhone(faker.numerify("##########")); //I believe only male and female is accepted
        minion.setGender(faker.gender().binaryTypes());
        return minion;
    }

    public static Minion getMinionById(int minionId){
        Response getResponse = given().accept(ContentType.JSON)
                .and().pathParam("id", minionId)
                .when().get("/minions/{id}");

        return getResponse.body().as(Minion.class);
    }

    public static Map<String, Object> getMinionMapById(int minionId){
        Response getResponse = given().accept(ContentType.JSON)
                .and().pathParam("id", minionId)
                .when().get("/minions/{id}");

        return getResponse.body().as(Map.class);
    }



}