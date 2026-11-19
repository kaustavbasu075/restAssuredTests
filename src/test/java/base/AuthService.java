package base;

import io.restassured.response.Response;

public class AuthService extends BaseService {

    private static final String key = "x-api-key";
    private static final String value = "free_user_3LvFV2iAfhn73M3q0nrlJ8U1BLy";

    // 1. JWT Bearer Token POST
    public Response postWithCachedJwt(String payload, String endpoint) {
        return postWithJwt(payload, endpoint);
    }

    // 2. Basic Auth GET
    public Response getBasicAuth(String username, String password, String endpoint) {
        return getWithBasicAuth(username, password, endpoint);
    }

    // 3. API Key GET
    public Response getApiKey(String headerName, String apiKey, String endpoint) {
        return getWithApiKey(headerName, apiKey, endpoint);
    }

    // 4. OAuth 2.0 GET
    public Response getOAuth2(String endpoint) {
        return getWithOAuth2(endpoint);
    }
    
    public Response login(Object payload, String endpoint) {
        return postRequest(key, value, payload, endpoint);
    }

    public Response createUser(Object payload, String endpoint) {
        return postUserRequest(payload, endpoint);
    }

    public Response fetchDetails(Object payload, String endpoint) {
        return postGraphQLQueryRequest(payload, endpoint);
    }
}
  