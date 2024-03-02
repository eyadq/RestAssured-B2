package io.loopcamp.api.spotifiy;

import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigurationReader;

import java.time.LocalDateTime;
import static io.restassured.RestAssured.given;

public class SpotifyAuthToken {


    private static final String CLIENT_ID = ConfigurationReader.getProperty("spotify.client.id");
    private static final String CLIENT_SECRET = ConfigurationReader.getProperty("spotify.client.secret");

    private String authToken;
    private String tokenType;
    private LocalDateTime timeExpired;

    public SpotifyAuthToken(){
        createToken();
    }

    private void createToken(){
        String payload = "grant_type=client_credentials&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;

        String SPOTIFY_ACCOUNTS_REQUEST_URL = "https://accounts.spotify.com/api/token";
        Response response =
                given().contentType("application/x-www-form-urlencoded")
                .and().body(payload)
                .when().post(SPOTIFY_ACCOUNTS_REQUEST_URL);

        timeExpired = LocalDateTime.now().plusSeconds(3600);

        JsonPath jsonPath = response.jsonPath();
        authToken = jsonPath.getString("access_token");
        tokenType = jsonPath.getString("token_type");
    }

    public Header getAuthToken(){
        if(isExpired())
            createToken();
        return new Header( "Authorization", "Bearer " + authToken);
    }

    private boolean isExpired() {
        return LocalDateTime.now().isAfter(timeExpired);
    }

}
