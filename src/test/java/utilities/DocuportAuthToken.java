package utilities;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DocuportAuthToken {

    private static final String AUTH_ENDPOINT = "https://beta.docuport.app/api/v1/authentication/account/authenticate";

    @Test
    public void authTokenGet(){
        String authToken = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(
                        Map.ofEntries(Map.entry("usernameOrEmailAddress", "b1g1_advisor@gmail.com"), Map.entry("password", "Group1"))

                )
                .when().post("https://beta.docuport.app/api/v1/authentication/account/authenticate")
                .then().log().all().extract().jsonPath().getString("user.jwtToken.accessToken");

        System.out.println(authToken);

        Map<String, String> authHeader = Map.of("Authorization", "Bearer " + authToken);
    }

    @Test
    public void testAuthToken(){
        System.out.println(getDocuportAuthHeader(ConfigurationReader.getProperty("advisor.username"), ConfigurationReader.getProperty("docuport.password")));
    }

    public static Map<String, String> getDocuportAuthHeader(String username, String password){
        String authToken = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(
                        Map.ofEntries(Map.entry("usernameOrEmailAddress", username), Map.entry("password", password))
                )
                .when().post(AUTH_ENDPOINT).then().extract().jsonPath().getString("user.jwtToken.accessToken");

        return Map.of("Authorization", "Bearer " + authToken);
    }
}
