package petTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class task2 extends TestBase{

    @Test
    public void test(){

        long id=9222968140498793998L;

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("petId",id)
                .when().get("pet/{petId}");
        assertEquals(response.statusCode(),200);
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertEquals(response.contentType(), "application/json");

        response.prettyPrint();

        Map<String, Object> receivedValues=response.body().as( Map.class);






    }


}
