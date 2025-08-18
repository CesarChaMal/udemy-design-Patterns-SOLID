package com.balazsholczer.apigateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.function.Function;

/**
 * Comprehensive test class for API Gateway Pattern - Traditional vs Modern approaches
 */
class APIGatewayTest {
    
    private APIGateway gateway;
    private UserService userService;
    private OrderService orderService;
    
    @BeforeEach
    void setUp() {
        gateway = new APIGateway();
        userService = new UserService();
        orderService = new OrderService();
        
        // Register services with path prefixes
        gateway.registerService("/users", userService);
        gateway.registerService("/orders", orderService);
    }
    
    @Test
    void testTraditionalAPIGateway() {
        // Traditional API Gateway routing
        Request userRequest = new Request("/users/123", "GET");
        userRequest.addHeader("Authorization", "Bearer valid-token");
        
        Response userResponse = gateway.routeRequest(userRequest);
        
        assertNotNull(userResponse);
        assertEquals(200, userResponse.getStatusCode());
        assertTrue(userResponse.getBody().contains("users"));
        assertEquals("APIGateway", userResponse.getHeaders().get("X-Gateway"));
        assertEquals("UserService", userResponse.getHeaders().get("X-Service"));
        
        Request orderRequest = new Request("/orders/456", "GET");
        orderRequest.addHeader("Authorization", "Bearer valid-token");
        
        Response orderResponse = gateway.routeRequest(orderRequest);
        
        assertNotNull(orderResponse);
        assertEquals(200, orderResponse.getStatusCode());
        assertTrue(orderResponse.getBody().contains("orders"));
        assertEquals("OrderService", orderResponse.getHeaders().get("X-Service"));
    }
    
    @Test
    void testAuthentication() {
        // Test authentication requirement
        Request unauthenticatedRequest = new Request("/users", "GET");
        Response unauthenticatedResponse = gateway.routeRequest(unauthenticatedRequest);
        
        assertEquals(401, unauthenticatedResponse.getStatusCode());
        assertTrue(unauthenticatedResponse.getBody().contains("Unauthorized"));
        
        // Test with valid authentication
        Request authenticatedRequest = new Request("/users", "GET");
        authenticatedRequest.addHeader("Authorization", "Bearer valid-token");
        
        Response authenticatedResponse = gateway.routeRequest(authenticatedRequest);
        assertEquals(200, authenticatedResponse.getStatusCode());
    }
    
    @Test
    void testServiceRouting() {
        // Test routing to different services
        Request userRequest = new Request("/users/profile", "GET");
        userRequest.addHeader("Authorization", "Bearer token");
        
        Response userResponse = gateway.routeRequest(userRequest);
        assertEquals(200, userResponse.getStatusCode());
        assertEquals("UserService", userResponse.getHeaders().get("X-Service"));
        
        Request orderRequest = new Request("/orders/history", "GET");
        orderRequest.addHeader("Authorization", "Bearer token");
        
        Response orderResponse = gateway.routeRequest(orderRequest);
        assertEquals(200, orderResponse.getStatusCode());
        assertEquals("OrderService", orderResponse.getHeaders().get("X-Service"));
    }
    
    @Test
    void testServiceNotFound() {
        // Test routing to non-existent service
        Request unknownRequest = new Request("/unknown/path", "GET");
        unknownRequest.addHeader("Authorization", "Bearer token");
        
        Response response = gateway.routeRequest(unknownRequest);
        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().contains("Service not found"));
    }
    
    @Test
    void testFunctionalApproach() {
        // Modern functional approach using lambda routing
        Map<String, Function<Request, Response>> functionalRoutes = new HashMap<>();
        
        functionalRoutes.put("/api/users", req -> 
            new Response(200, "{\"functional\": \"user data\"}"));
        functionalRoutes.put("/api/orders", req -> 
            new Response(200, "{\"functional\": \"order data\"}"));
        
        // Test functional routing
        Function<Request, Response> userRoute = functionalRoutes.get("/api/users");
        Function<Request, Response> orderRoute = functionalRoutes.get("/api/orders");
        
        Request userReq = new Request("/api/users", "GET");
        Request orderReq = new Request("/api/orders", "GET");
        
        Response userResp = userRoute.apply(userReq);
        Response orderResp = orderRoute.apply(orderReq);
        
        assertEquals(200, userResp.getStatusCode());
        assertEquals(200, orderResp.getStatusCode());
        assertTrue(userResp.getBody().contains("user data"));
        assertTrue(orderResp.getBody().contains("order data"));
    }
    
    @Test
    void testRequestResponseHandling() {
        // Test request and response objects
        Request request = new Request("/users/123", "POST");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer token");
        request.setBody("{\"name\": \"John\"}");
        
        assertEquals("/users/123", request.getPath());
        assertEquals("POST", request.getMethod());
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
        assertEquals("{\"name\": \"John\"}", request.getBody());
        
        Response response = new Response(201, "{\"id\": 123}");
        response.addHeader("Location", "/users/123");
        
        assertEquals(201, response.getStatusCode());
        assertEquals("{\"id\": 123}", response.getBody());
        assertEquals("/users/123", response.getHeaders().get("Location"));
    }
    
    @Test
    void testServiceImplementations() {
        // Test individual service implementations
        UserService userSvc = new UserService();
        OrderService orderSvc = new OrderService();
        
        assertEquals("UserService", userSvc.getServiceName());
        assertEquals("OrderService", orderSvc.getServiceName());
        
        Request userReq = new Request("/users/list", "GET");
        Request orderReq = new Request("/orders/list", "GET");
        Request invalidReq = new Request("/invalid", "GET");
        
        Response userResp = userSvc.handleRequest(userReq);
        Response orderResp = orderSvc.handleRequest(orderReq);
        Response invalidResp = userSvc.handleRequest(invalidReq);
        
        assertEquals(200, userResp.getStatusCode());
        assertEquals(200, orderResp.getStatusCode());
        assertEquals(404, invalidResp.getStatusCode());
    }
    
    @Test
    void testModernStreamBasedProcessing() {
        // Modern approach using streams for batch processing
        List<Request> requests = Arrays.asList(
            new Request("/users/1", "GET"),
            new Request("/users/2", "GET"),
            new Request("/users/3", "GET")
        );
        
        // Add auth headers to all requests
        requests.forEach(req -> req.addHeader("Authorization", "Bearer token"));
        
        // Process all requests
        List<Response> responses = requests.stream()
            .map(gateway::routeRequest)
            .toList();
        
        assertEquals(3, responses.size());
        responses.forEach(resp -> {
            assertEquals(200, resp.getStatusCode());
            assertEquals("UserService", resp.getHeaders().get("X-Service"));
        });
    }
    
    @Test
    void testServiceComposition() {
        // Test composing multiple services functionally
        Function<Request, Response> authFilter = req -> {
            if (req.getHeaders().get("Authorization") == null) {
                return new Response(401, "{\"error\": \"No auth\"}");
            }
            return null; // Continue processing
        };
        
        Function<Request, Response> userProcessor = req -> 
            new Response(200, "{\"user\": \"processed\"}");
        
        // Compose filters and processors
        Function<Request, Response> composedService = req -> {
            Response authResp = authFilter.apply(req);
            if (authResp != null) return authResp;
            return userProcessor.apply(req);
        };
        
        Request authReq = new Request("/test", "GET");
        authReq.addHeader("Authorization", "Bearer token");
        
        Request noAuthReq = new Request("/test", "GET");
        
        Response authResp = composedService.apply(authReq);
        Response noAuthResp = composedService.apply(noAuthReq);
        
        assertEquals(200, authResp.getStatusCode());
        assertEquals(401, noAuthResp.getStatusCode());
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify traditional and functional approaches produce equivalent results
        Request testRequest = new Request("/users/test", "GET");
        testRequest.addHeader("Authorization", "Bearer token");
        
        // Traditional approach
        Response traditionalResponse = gateway.routeRequest(testRequest);
        
        // Functional approach
        Function<Request, Response> functionalGateway = req -> {
            if (req.getPath().startsWith("/users")) {
                return new Response(200, "{\"users\": [\"john\", \"jane\"]}");
            }
            return new Response(404, "{\"error\": \"Not found\"}");
        };
        
        Response functionalResponse = functionalGateway.apply(testRequest);
        
        // Both should return 200 and contain user data
        assertEquals(traditionalResponse.getStatusCode(), functionalResponse.getStatusCode());
        assertTrue(traditionalResponse.getBody().contains("users"));
        assertTrue(functionalResponse.getBody().contains("users"));
    }
}