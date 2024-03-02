package io.loopcamp.api.pet_store;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class PetStoreApiTest {

    @BeforeAll
    public static void setBaseURL(){
        baseURI = baseUrl;
    }

    static String baseUrl  = "https://petstore.swagger.io/v2";
    Map<String, String> getApiKey = Map.of("api_key", "special-key");
    RequestSpecBuilder request = new RequestSpecBuilder();;
    Response response;
    ValidatableResponse validate;

    @DisplayName("GET /pet/{petId}")
    @Test
    public void getPetTest(){
        int petId = 1;

        request.addQueryParams(getApiKey);
        request.setAccept(ContentType.JSON);

        response = given().spec(request.build()).when().get("/pet/{petId}", petId);
        //response.prettyPrint();
        validate = response.then();

        validate.statusCode(HttpStatus.SC_OK);
        validate.body("id", is(petId));

        given()
                .queryParam("api_key", "special-key")
                .accept(ContentType.JSON)
        .when()
                .get("/pet/{petId}", petId)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(petId));
    }

    @DisplayName("POST /pet")
    @Test
    public void postPetTest(){
        int petId = 654;


        NewPetPojo pet = new NewPetPojo();
        pet.setId(petId);
        NewPetPojo.Category category = new NewPetPojo.Category();
        category.setId(0);
        category.setName("dog");
        pet.setCategory(category);
        pet.setName("Ren");
        pet.setPhotoUrls(Arrays.asList("pic1"));
        List<NewPetPojo.Category> tags = new ArrayList<>();
        NewPetPojo.Category tagCategory = new NewPetPojo.Category();
        tagCategory.setId(0);
        tagCategory.setName("show dog");
        tags.add(tagCategory);
        pet.setTags(tags);
        pet.setStatus("available");


        String body = String.format( // note: %d by id will be replaced with variable petId
                "{\n" +
                "  \"id\": %d,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"Ren\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}", petId);




        given()
                .queryParams(getApiKey)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(petId))
                .log().all();
    }

}
