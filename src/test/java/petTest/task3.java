package petTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class task3 extends TestBase{


    @Test

    public void test(){

        long id=9222968140498794013L;
        Response response=given().accept(ContentType.JSON)
                .and().pathParam("petId",id)
                .when().delete("pet/{petId}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json");

        String type=response.path("type");
        String message= response.path("message");
        assertEquals(type, "unknown");
        assertEquals(message,"9222968140498794013");

    }



}
