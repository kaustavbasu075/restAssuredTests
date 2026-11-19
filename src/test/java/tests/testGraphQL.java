package tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.AuthService;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class testGraphQL {

    @Test(description = "GraphQL Test 1")
    public void testgraphQL() {
        
        AuthService authService = new AuthService();
        Response response = authService.fetchDetails("{\"query\":\"query ($characterId: Int!, $locationId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  location(locationId: $locationId) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: 1) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"Rahul\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"hulu\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":8,\"locationId\":12}}", "/gq/graphql");
        System.out.println(response.asPrettyString());

        JsonPath jsonPath = new JsonPath(response.asString());
        String locationId = jsonPath.getString("data.characters.info.count");
        Assert.assertNotNull(locationId);
    }

    @Test(description = "GraphQL Test 2")
    public void mutuationgraphQL() {
        
        AuthService authService = new AuthService();
        Response response = authService.fetchDetails("{\"query\":\"mutation($locationName: String!,$episodeName: String!){\\n  createLocation(location: {name: $locationName,type: \\\"Test\\\",dimension: \\\"123\\\"}) {\\n    id\\n  }\\n  createEpisode(episode: {name: $episodeName,air_date: \\\"1990 July\\\",episode: \\\"Prime\\\"}) {\\n    id\\n  }\\n  \\n  deleteLocations(locationIds: [12]) {\\n    locationsDeleted\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"Test\",\"episodeName\":\"Netflix\"}}", "/gq/graphql");
        System.out.println(response.asPrettyString());

        JsonPath jsonPath = new JsonPath(response.asString());
        String locationId = jsonPath.getString("data.createEpisode.id");
        Assert.assertNotNull(locationId);
    }

}
