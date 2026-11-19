package filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class LoggingFilter implements Filter {
    
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);
    
    @Override
    public Response filter(FilterableRequestSpecification request, FilterableResponseSpecification response, FilterContext context) {
        logger.info("Request: " + request);
        logger.info("Response: " + response);
        logRequest(request);
        Response resp = context.next(request, response);// Request is being made to the server, and the response is being received.
        logResponse(resp);
        return resp;
        
    }

    public void logRequest(FilterableRequestSpecification request) {
        logger.info("Request: " + request);
        logger.info("Base URI: " + request.getBaseUri());
        logger.info("Request Headers: " + request.getHeaders());
        logger.info("Request Body: " + request.getBody());
    }

    public void logResponse(Response response) {
           logger.info("Response: " + response);
           logger.info("Response Status Code: " + response.getStatusCode());
           logger.info("Response Headers: " + response.getHeaders());
           logger.info("Response Body: " + response.getBody().asString());

    }

}
