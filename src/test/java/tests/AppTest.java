package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import listeners.ListenersFile;

/**
 * Unit test for simple App.
 */
@Listeners(ListenersFile.class)
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test(description = "Login Test")
    public void login()
    {
        RestAssured.baseURI = "https://reqres.in/";
        RequestSpecification request = RestAssured.given();
        RequestSpecification request1 = request.header("Content-Type", "application/json");
        RequestSpecification request2 = request1.header("x-api-key", "free_user_3LvFV2iAfhn73M3q0nrlJ8U1BLy");
        RequestSpecification request3 = request2.body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}");
        Response response = request3.post("/api/login");
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);

    }
}
