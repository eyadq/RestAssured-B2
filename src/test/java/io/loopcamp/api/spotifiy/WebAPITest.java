package io.loopcamp.api.spotifiy;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;

public class WebAPITest {

    SpotifyAuthToken spotifyAuthToken = new SpotifyAuthToken();

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("spotify.base.url");
    }

    @DisplayName("Get /artists with Mohammad Assaf")
    @Test
    public void getSingleArtist(){
        String artistID = "0IjIdnhlsKfAfOl5ph5TsE";

        Response response =
                given().pathParam("artistID", artistID)
                        .and().header(spotifyAuthToken.getAuthToken())
                        .when().get("/artists/{artistID}");
        response.prettyPrint();
    }
}
