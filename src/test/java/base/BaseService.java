package base;
import static io.restassured.RestAssured.given;

import filters.LoggingFilter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.AuthManager;
import utils.ConfigReader;

public class BaseService {


    private static final String BaseURL = ConfigReader.get("base.url");
    private RequestSpecification request;

    {
        RestAssured.filters(new LoggingFilter());
    }

    public BaseService(){
            request = given().baseUri(BaseURL);

    }

    protected Response postRequest(String authKey,String authValue, Object payload, String endpoint) {
        return request.header(authKey, authValue).contentType(ContentType.JSON).body(payload).post(endpoint);
    }

    protected Response postUserRequest(Object payload, String endpoint) {
        return request.contentType(ContentType.JSON).body(payload).post(endpoint);
    }

     protected Response postGraphQLQueryRequest(Object payload, String endpoint) {
        return request.contentType(ContentType.JSON).body(payload).post(endpoint);
    }

    // 1. Basic Auth GET
    protected Response getWithBasicAuth(String user, String pass, String endpoint) {
        return given()
                .spec(AuthManager.getBasicAuthSpec(BaseURL, user, pass))
                .get(endpoint);
    }

    // 2. API Key GET
    protected Response getWithApiKey(String headerName, String apiKey, String endpoint) {
        return given()
                .spec(AuthManager.getApiKeyHeaderSpec(BaseURL, headerName, apiKey))
                .get(endpoint);
    }

    // 3. Cached JWT POST
    protected Response postWithJwt(Object payload, String endpoint) {
        return given()
                .spec(AuthManager.getJwtAuthSpec(BaseURL))
                .body(payload)
                .post(endpoint);
    }

    // 4. Cached OAuth 2.0 GET
    protected Response getWithOAuth2(String endpoint) {
        return given()
                .spec(AuthManager.getOAuth2Spec(BaseURL))
                .get(endpoint);
    }

}


