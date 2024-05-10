package petTest;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Interview {

    static long id;
    static JsonPath firstTest;

    @BeforeAll
    public static void init(){
        baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void test1(){

//        String body = "{\n" +
//                "\"category\": {\n" +
//                "\"id\": 0,\n" +
//                "\"name\": \"Pets\"\n" +
//                "},\n" +
//                "\"name\": \"Scout\",\n" +
//                "\"photoUrls\": [\n" +
//                "\"scout.png\"\n" +
//                "],\n" +
//                "\"tags\": [\n" +
//                "{\n" +
//                "\"id\": 0,\n" +
//                "\"name\": \"pet-dog\"\n" +
//                "}\n" +
//                "],\n" +
//                "\"status\": \"available\"\n" +
//                "}";

        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> category = new LinkedHashMap<>();
        List<Map<String, Object>> tags = new ArrayList<>();
        Map<String, Object> tagsArrayInsides = new LinkedHashMap<>();
        List<String> photos = new ArrayList<>();

        category.put("id", 0);
        category.put("name", "Pets");

        tagsArrayInsides.put("id", 0);
        tagsArrayInsides.put("name", "pet-dog");

        tags.add(tagsArrayInsides);

        photos.add("scout.png");

        body.put("category", category);
        body.put("name", "Scout");
        body.put("photoUrls", photos);
        body.put("tags", tags);
        body.put("status", "available");


       JsonPath jsonPath = given().contentType(ContentType.JSON).and().accept(ContentType.JSON)
                .body(body)
                .when().post("/pet").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .header("Date", notNullValue())
                .body("id", is(notNullValue()),
                "name", is(body.get("name")))
               .extract().jsonPath();

        id = jsonPath.getLong("id");
        firstTest = jsonPath;
    }

    @Test
    public void test2(){

      JsonPath secondJson =  given().accept(ContentType.JSON).pathParam("petId", id)
                .when().get("/pet/{petId}")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .header("Date", notNullValue())
                .extract().jsonPath();
        Map<String, Object> secondResponse = secondJson.getMap("");
        Map<String, Object> firstResponse = firstTest.getMap("");

        Assertions.assertEquals(firstResponse,secondResponse);
    }

    @Test
    public void test3(){

        given().accept(ContentType.JSON).pathParam("petId", id)
                .when().delete("/pet/{petId}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("message", is(String.valueOf(id)),
                        "type", is("unknown"));

    }

}