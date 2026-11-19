package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Unit test for simple App.
 */
public class AppTest1
{
    /**
     * Rigorous Test :-)
     */
    // @BeforeMethod
    // public void logThreadInfo(Method method) {
    //     System.out.println("Starting [" + method.getName() + "] on Thread ID: " + Thread.currentThread().threadId());
    // }

    @Test(description = "Login Test 1")
    public void login()
    {
        RestAssured.baseURI = "https://reqres.in/";
        Response response = RestAssured.given().header("Content-Type", "application/json").header("x-api-key", "free_user_3LvFV2iAfhn73M3q0nrlJ8U1BLy").body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}").post("/api/login");
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);

    }
}
