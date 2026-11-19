package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.AuthService;
import io.restassured.response.Response;
import models.Request.CreateUserRequest;
import models.Request.LoginRequest;
import models.Response.CreateUserResponse;
import models.Response.LoginResponse;

/**
 * Unit test for simple App.
 */
public class AppTest2
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
        LoginRequest loginRequest = new LoginRequest("eve.holt@reqres.in", "cityslicka");
        AuthService authService = new AuthService();
        Response response = authService.login(loginRequest,"/api/login");
        LoginResponse loginResponse = response.as(LoginResponse.class);

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(loginResponse.getToken());
        Assert.assertNotNull(loginResponse.getSupport());
        Assert.assertNotNull(loginResponse.getMeta());

        Assert.assertNotNull(loginResponse.getSupport().getUrl());
        Assert.assertNotNull(loginResponse.getSupport().getText());
        Assert.assertNotNull(loginResponse.getSupport().getSponsoredBy());

        Assert.assertNotNull(loginResponse.getMeta().getPoweredBy());
        Assert.assertNotNull(loginResponse.getMeta().getDocsUrl());
        Assert.assertNotNull(loginResponse.getMeta().getUpgradeUrl());
        Assert.assertNotNull(loginResponse.getMeta().getExampleUrl());
        Assert.assertNotNull(loginResponse.getMeta().getVariant());
        Assert.assertNotNull(loginResponse.getMeta().getMessage());
        Assert.assertNotNull(loginResponse.getMeta().getCta());

        Assert.assertNotNull(loginResponse.getMeta().getCta().getLabel());
        Assert.assertNotNull(loginResponse.getMeta().getCta().getUrl());
    }

    @Test(description = "Create Test 1")
    public void createUser(){
         
        CreateUserRequest createUserRequest = new CreateUserRequest.Builder().setBody("foo").setTitle("bar").setUserId( 1).build();
        AuthService authService = new AuthService();
        Response response = authService.createUser(createUserRequest,"/posts");
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 201);

        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        Assert.assertNotNull(createUserResponse.getTitle());
        Assert.assertNotNull(createUserResponse.getBody());
        Assert.assertNotNull(createUserResponse.getUserId());
        Assert.assertNotNull(createUserResponse.getId());

    }

}
