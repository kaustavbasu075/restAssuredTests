package utils;

import static io.restassured.RestAssured.given;

import java.time.Instant;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthManager {

    // Cached tokens and expiry timestamps for dynamic flows
    private static String cachedJwtToken;
    private static Instant jwtExpiryTime = Instant.EPOCH;

    private static String cachedOAuthToken;
    private static Instant oAuthExpiryTime = Instant.EPOCH;

    private AuthManager() {
        // Prevent instantiation
    }

    // =========================================================================
    // 1. BASIC AUTH (Static credentials wrapped in a reusable RequestSpec)
    // =========================================================================
    public static RequestSpecification getBasicAuthSpec(String baseUri, String username, String password) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .build()
                .auth().preemptive().basic(username, password);
    }

    // =========================================================================
    // 2. API KEY AUTH (Header or Query Param wrapped in a reusable RequestSpec)
    // =========================================================================
    public static RequestSpecification getApiKeyHeaderSpec(String baseUri, String headerName, String apiKey) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .addHeader(headerName, apiKey)
                .build();
    }

    public static RequestSpecification getApiKeyQuerySpec(String baseUri, String paramName, String apiKey) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .addQueryParam(paramName, apiKey)
                .build();
    }

    // =========================================================================
    // 3. JWT BEARER TOKEN (Cached & auto-refreshed with thread safety)
    // =========================================================================
    public static synchronized String getJwtToken() {
        // Refresh if token is null or within 30 seconds of expiring
        if (cachedJwtToken == null || Instant.now().isAfter(jwtExpiryTime.minusSeconds(30))) {
            String loginUrl = ConfigReader.get("jwt.login.url"); // e.g. https://dummyjson.com
            String username = ConfigReader.get("jwt.username");  // e.g. emilys
            String password = ConfigReader.get("jwt.password");  // e.g. emilyspass

            String payload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .post(loginUrl);

            response.then().statusCode(200);

            cachedJwtToken = response.jsonPath().getString("accessToken");
            // Default to 15 mins if API doesn't return an 'expiresIn' field
            int expiresInSeconds = response.jsonPath().get("expiresIn") != null
                    ? response.jsonPath().getInt("expiresIn") * 60
                    : 900;
            jwtExpiryTime = Instant.now().plusSeconds(expiresInSeconds);
        }
        return cachedJwtToken;
    }

    public static RequestSpecification getJwtAuthSpec(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .build()
                .auth().oauth2(getJwtToken());
    }

    // =========================================================================
    // 4. OAUTH 2.0 CLIENT CREDENTIALS (Cached & auto-refreshed)
    // =========================================================================
    public static synchronized String getOAuth2Token() {
        if (cachedOAuthToken == null || Instant.now().isAfter(oAuthExpiryTime.minusSeconds(30))) {
            String tokenUrl = ConfigReader.get("oauth.token.url");
            String clientId = ConfigReader.get("oauth.client.id");
            String clientSecret = ConfigReader.get("oauth.client.secret");

            Response response = given()
                    .contentType(ContentType.URLENC)
                    .formParam("grant_type", "client_credentials")
                    .formParam("client_id", clientId)
                    .formParam("client_secret", clientSecret)
                    .post(tokenUrl);

            response.then().statusCode(200);

            cachedOAuthToken = response.jsonPath().getString("access_token");
            int expiresIn = response.jsonPath().getInt("expires_in"); // OAuth2 returns seconds
            oAuthExpiryTime = Instant.now().plusSeconds(expiresIn);
        }
        return cachedOAuthToken;
    }

    public static RequestSpecification getOAuth2Spec(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .build()
                .auth().oauth2(getOAuth2Token());
    }

    // Optional: Force clear tokens if testing negative/expired token scenarios
    public static synchronized void clearTokens() {
        cachedJwtToken = null;
        cachedOAuthToken = null;
    }
}

