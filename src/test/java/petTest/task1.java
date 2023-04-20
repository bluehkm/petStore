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
    public static String responsBody;
    public static long id;


    @Test
    public void test1(){


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
        responsBody= response.asString();
        assertTrue(responsBody.contains("Scout"));
        System.out.println(postedValues.get("id"));
        id=(long)postedValues.get("id");
        System.out.println(id);

    }

    @Test
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("petId",id)
                .when().get("pet/{petId}");
        assertEquals(response.statusCode(),200);
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertEquals(response.contentType(), "application/json");

        response.prettyPrint();

        Map<String, Object> receivedValues=response.body().as( Map.class);
        assertEquals(response.asString(),task1.responsBody);

        System.out.println("again: "+id);

    }

    @Test

    public void test3(){

        Response response=given().accept(ContentType.JSON)
                .and().pathParam("petId",id)
                .when().delete("pet/{petId}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json");

        String type=response.path("type");
        String message= response.path("message");
        assertEquals(type, "unknown");
        assertEquals(message,Long.toString(id));

    }


}




