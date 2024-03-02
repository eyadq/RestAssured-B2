package io.loopcamp.tasks.day02_tasks;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.Constants;

import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilities.Constants.*;

public class JsonPlaceHolder {

    @DisplayName("GET /posts")
    @Test
    public void testPosts(){
        Response response = when().get(JSON_PLACE_HOLDER_POSTS_BASE);

        response.then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON);
        response.prettyPrint();

    }

    @DisplayName("GET /posts/{id} when id=1")
    @Test
    public void testPostsIdIs1(){
        Response response =
                given().accept(ContentType.JSON)
                .and().pathParam("id", 1)
                .when().get(JSON_PLACE_HOLDER_POSTS_BASE + "/{id}");

        response
                .then().statusCode(HttpStatus.SC_OK)
                .and().header("X-Powered-By", "Express")
                .and().header("X-Ratelimit-Limit", "1000");
        assertTrue(Integer.parseInt(response.header("Age")) > 100);
        assertTrue(response.header("Nel").contains("success"));

        response.prettyPrint();
    }

    @DisplayName("GET /posts/{id} when id=12345")
    @Test
    public void testPostsIdIs12345(){
        Response response =
                given().accept(ContentType.JSON)
                        .and().pathParam("id", 12345)
                        .when().get(JSON_PLACE_HOLDER_POSTS_BASE + "/{id}");

        System.out.println(response.asString());

        response
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
        assertTrue(response.body().asString().contains("{}"));
    }

    @DisplayName("GET /posts/{id} when id=2")
    @Test
    public void testPostsIdIs2() {
        Response response =
                given().accept(ContentType.JSON)
                        .and().pathParam("id", 2)
                        .when().get(JSON_PLACE_HOLDER_POSTS_BASE + "/{id}/comments");

        response.
                then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().header("Content-Type", ContentType.JSON.withCharset(StandardCharsets.UTF_8).toString().toLowerCase());

        String[] emails = {"Presley.Mueller@myrl.com", "Dallas@ole.me", "Mallory_Kunze@marie.org"};
        for (String email: emails){
            assertTrue(response.body().asString().contains(email));
        }

        response.prettyPrint();
    }

    @DisplayName("GET /comments when id=1")
    @Test
    public void testCommentsPostIdIs1() {
        Response response =
                given().accept(ContentType.JSON)
                        .and().pathParam("postId", 1)
                        .when().get(JSON_PLACE_HOLDER_COMMENTS_BASE + "/{postId}");

        response.prettyPrint();

        response.
                then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().header("Content-Type", ContentType.JSON.withCharset(StandardCharsets.UTF_8).toString().toLowerCase())
                .and().assertThat().header("Connection", "keep-alive");

        assertFalse(response.body().asString().contains("Lew@alysha.tv"));
        assertTrue(response.body().asString().contains("Eliseo@gardner.biz"));

    }

    @DisplayName("GET /comments when id=333")
    @Test
    public void testCommentsPostIdIs333() {
        Response response =
                given().accept(ContentType.JSON)
                        .and().queryParam("postId", 1)
                        .when().get("https://jsonplaceholder.typicode.com/comments" + "/{postId}");

        response.prettyPrint();

        response.
                then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().header("Content-Type", ContentType.JSON.withCharset(StandardCharsets.UTF_8).toString().toLowerCase())
                .and().assertThat().header("Content-Length", "2");

        assertFalse(response.body().asString().contains("Lew@alysha.tv"));
        assertTrue(response.body().asString().contains("{}"));

    }

}
