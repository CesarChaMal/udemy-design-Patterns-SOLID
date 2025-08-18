package com.balazsholczer.frontcontroller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.BiConsumer;

/**
 * Comprehensive test class for Front Controller Pattern - Traditional vs Modern approaches
 */
class FrontControllerPatternTest {
    
    private FrontController frontController;
    private FunctionalFrontController functionalController;
    private Dispatcher dispatcher;
    
    @BeforeEach
    void setUp() {
        frontController = new FrontController();
        functionalController = new FunctionalFrontController();
        dispatcher = new Dispatcher();
    }
    
    @Test
    void testTraditionalFrontController() {
        // Traditional front controller pattern
        Request homeRequest = new Request("/home", "GET");
        Response homeResponse = frontController.processRequest(homeRequest);
        
        assertNotNull(homeResponse);
        assertEquals(200, homeResponse.getStatusCode());
        assertTrue(homeResponse.getContent().contains("Home"));
        
        Request userRequest = new Request("/user", "GET");
        Response userResponse = frontController.processRequest(userRequest);
        
        assertNotNull(userResponse);
        assertEquals(200, userResponse.getStatusCode());
        assertTrue(userResponse.getContent().contains("User"));
    }
    
    @Test
    void testFunctionalFrontController() {
        // Functional approach using lambda routing
        Request homeRequest = new Request("/", "GET");
        Response homeResponse = functionalController.processRequest(homeRequest);
        
        assertEquals(200, homeResponse.getStatusCode());
        assertTrue(homeResponse.getContent().contains("Functional Home"));
        assertEquals("text/html", homeResponse.getContentType());
        
        Request apiRequest = new Request("/api/users", "GET");
        Response apiResponse = functionalController.processRequest(apiRequest);
        
        assertEquals(200, apiResponse.getStatusCode());
        assertTrue(apiResponse.getContent().contains("users"));
        assertEquals("application/json", apiResponse.getContentType());
    }
    
    @Test
    void testRequestDispatcher() {
        // Test dispatcher functionality
        Request request = new Request("/home", "GET");
        Response response = new Response();
        
        dispatcher.dispatch(request, response);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        
        // Test unknown route
        Request unknownRequest = new Request("/unknown", "GET");
        Response unknownResponse = new Response();
        dispatcher.dispatch(unknownRequest, unknownResponse);
        
        assertEquals(404, unknownResponse.getStatusCode());
    }
    
    @Test
    void testCommandPattern() {
        // Test command pattern integration
        Command homeCommand = new HomeCommand();
        Command userCommand = new UserCommand();
        
        frontController.registerCommand("/home", homeCommand);
        frontController.registerCommand("/user", userCommand);
        
        Request homeRequest = new Request("/home", "GET");
        Response homeResponse = frontController.processRequest(homeRequest);
        
        assertNotNull(homeResponse);
        assertTrue(homeResponse.getContent().contains("Home"));
        
        Request userRequest = new Request("/user", "GET");
        Response userResponse = frontController.processRequest(userRequest);
        
        assertNotNull(userResponse);
        assertTrue(userResponse.getContent().contains("User"));
    }
    
    @Test
    void testRequestPreprocessing() {
        // Test request preprocessing
        Request request = new Request("/test", "GET");
        
        // Process request to trigger preprocessing
        frontController.processRequest(request);
        
        // Check that preprocessing added request time
        assertNotNull(request.getAttribute("requestTime"));
        assertTrue(request.getAttribute("requestTime") instanceof Long);
    }
    
    @Test
    void testResponsePostprocessing() {
        // Test response postprocessing
        Request request = new Request("/home", "GET");
        
        long startTime = System.currentTimeMillis();
        Response response = frontController.processRequest(request);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        
        // Verify processing time was calculated (attribute was set during preprocessing)
        Long requestTime = (Long) request.getAttribute("requestTime");
        assertNotNull(requestTime);
        assertTrue(requestTime <= System.currentTimeMillis());
    }
    
    @Test
    void testRequestParameters() {
        // Test request parameter handling
        Request request = new Request("/user", "GET");
        request.setParameter("id", "123");
        request.setParameter("name", "john");
        
        assertEquals("123", request.getParameter("id"));
        assertEquals("john", request.getParameter("name"));
        assertNull(request.getParameter("nonexistent"));
    }
    
    @Test
    void testRequestAttributes() {
        // Test request attribute handling
        Request request = new Request("/test", "POST");
        request.setAttribute("user", "testUser");
        request.setAttribute("role", "admin");
        
        assertEquals("testUser", request.getAttribute("user"));
        assertEquals("admin", request.getAttribute("role"));
        assertNull(request.getAttribute("nonexistent"));
    }
    
    @Test
    void testResponseProperties() {
        // Test response creation and properties
        Response response = new Response();
        
        // Default values
        assertEquals(200, response.getStatusCode());
        assertEquals("text/html", response.getContentType());
        
        // Set custom values
        response.setStatusCode(404);
        response.setContent("Not Found");
        response.setContentType("application/json");
        
        assertEquals(404, response.getStatusCode());
        assertEquals("Not Found", response.getContent());
        assertEquals("application/json", response.getContentType());
    }
    
    @Test
    void testMultipleRequestMethods() {
        // Test different HTTP methods
        Request getRequest = new Request("/api/users", "GET");
        Request postRequest = new Request("/api/users", "POST");
        Request putRequest = new Request("/api/users/123", "PUT");
        Request deleteRequest = new Request("/api/users/123", "DELETE");
        
        assertEquals("GET", getRequest.getMethod());
        assertEquals("POST", postRequest.getMethod());
        assertEquals("PUT", putRequest.getMethod());
        assertEquals("DELETE", deleteRequest.getMethod());
        
        Response getResponse = frontController.processRequest(getRequest);
        Response postResponse = frontController.processRequest(postRequest);
        Response putResponse = frontController.processRequest(putRequest);
        Response deleteResponse = frontController.processRequest(deleteRequest);
        
        // All should be handled appropriately
        assertNotNull(getResponse);
        assertNotNull(postResponse);
        assertNotNull(putResponse);
        assertNotNull(deleteResponse);
    }
    
    @Test
    void testFunctionalControllerRoutes() {
        // Test all predefined functional routes
        Request homeRequest = new Request("/", "GET");
        Response homeResponse = functionalController.processRequest(homeRequest);
        assertEquals(200, homeResponse.getStatusCode());
        assertTrue(homeResponse.getContent().contains("Functional Home"));
        
        Request usersRequest = new Request("/api/users", "GET");
        Response usersResponse = functionalController.processRequest(usersRequest);
        assertEquals(200, usersResponse.getStatusCode());
        assertTrue(usersResponse.getContent().contains("john"));
        assertEquals("application/json", usersResponse.getContentType());
        
        Request ordersRequest = new Request("/api/orders", "GET");
        Response ordersResponse = functionalController.processRequest(ordersRequest);
        assertEquals(200, ordersResponse.getStatusCode());
        assertTrue(ordersResponse.getContent().contains("orders"));
        assertEquals("application/json", ordersResponse.getContentType());
    }
    
    @Test
    void testErrorHandling() {
        // Test error handling for unknown routes
        Request unknownRequest = new Request("/nonexistent", "GET");
        
        // Traditional controller
        Response traditionalResponse = frontController.processRequest(unknownRequest);
        assertNotNull(traditionalResponse);
        assertEquals(404, traditionalResponse.getStatusCode());
        
        // Functional controller
        Response functionalResponse = functionalController.processRequest(unknownRequest);
        assertNotNull(functionalResponse);
        assertEquals(404, functionalResponse.getStatusCode());
        assertTrue(functionalResponse.getContent().contains("Not Found"));
        assertEquals("application/json", functionalResponse.getContentType());
    }
    
    @Test
    void testRequestToString() {
        // Test request string representation
        Request request = new Request("/test", "GET");
        request.setParameter("param1", "value1");
        
        String requestString = request.toString();
        assertTrue(requestString.contains("GET"));
        assertTrue(requestString.contains("/test"));
        assertTrue(requestString.contains("param1"));
    }
    
    @Test
    void testResponseToString() {
        // Test response string representation
        Response response = new Response();
        response.setStatusCode(200);
        response.setContent("Test content");
        response.setContentType("text/plain");
        
        String responseString = response.toString();
        assertTrue(responseString.contains("200"));
        assertTrue(responseString.contains("Test content"));
        assertTrue(responseString.contains("text/plain"));
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify traditional and functional approaches can handle similar requests
        Request testRequest = new Request("/", "GET");
        
        // Functional controller handles root path
        Response functionalResponse = functionalController.processRequest(testRequest);
        assertEquals(200, functionalResponse.getStatusCode());
        
        // Traditional controller with registered command
        frontController.registerCommand("/", new HomeCommand());
        Response traditionalResponse = frontController.processRequest(testRequest);
        assertEquals(200, traditionalResponse.getStatusCode());
        
        // Both should successfully handle the request
        assertNotNull(functionalResponse.getContent());
        assertNotNull(traditionalResponse.getContent());
    }
    
    @Test
    void testContentTypeHandling() {
        // Test different content types
        Request htmlRequest = new Request("/", "GET");
        Response htmlResponse = functionalController.processRequest(htmlRequest);
        assertEquals("text/html", htmlResponse.getContentType());
        
        Request jsonRequest = new Request("/api/users", "GET");
        Response jsonResponse = functionalController.processRequest(jsonRequest);
        assertEquals("application/json", jsonResponse.getContentType());
        
        Request ordersRequest = new Request("/api/orders", "GET");
        Response ordersResponse = functionalController.processRequest(ordersRequest);
        assertEquals("application/json", ordersResponse.getContentType());
    }
}