package petTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;


public class task1 extends TestBase{

    @Test
    public void test(){


        String body = "{\n" +
                "\"category\": {\n" +
                "\"id\": 0,\n" +
                "\"name\": \"Pets\"\n" +
                "},\n" +
                "\"name\": \"Scout\",\n" +
                "\"photoUrls\": [\n" +
                "\"scout.png\"\n" +
                "],\n" +
                "\"tags\": [\n" +
                "{\n" +
                "\"id\": 0,\n" +
                "\"name\": \"pet-dog\"\n" +
                "}\n" +
                "],\n" +
                "\"status\": \"available\"\n" +
                "}";


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(body)
                .when().post("pet/");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json");
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertThat(response.path("id"), is(notNullValue()));
        response.prettyPrint();


        Map<String, Object> postedValues = response.body().as(Map.class);



    }

    }




