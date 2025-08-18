package com.balazsholczer.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectionPatternTest {

    private DIContainer container;
    private FunctionalDIContainer functionalContainer;

    @BeforeEach
    void setUp() {
        container = new DIContainer();
        functionalContainer = new FunctionalDIContainer();
    }

    @Test
    void testTraditionalDependencyInjection() {
        container.registerSingleton(UserRepository.class, new DatabaseUserRepository());
        UserService userService = container.createInstance(UserService.class);
        assertNotNull(userService);
        userService.createUser("1", "John Doe", "john@example.com");
        User user = userService.getUser("1");
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
    }

    @Test
    void testFunctionalDependencyInjection() {
        functionalContainer.register("UserRepository", DatabaseUserRepository::new);
        functionalContainer.register("UserService", () -> {
            UserRepository repo = (UserRepository) functionalContainer.resolve("UserRepository");
            return new UserService(repo);
        });
        UserService userService = (UserService) functionalContainer.resolve("UserService");
        assertNotNull(userService);
        userService.createUser("2", "Jane Doe", "jane@example.com");
        User user = userService.getUser("2");
        assertNotNull(user);
        assertEquals("Jane Doe", user.getName());
    }

    @Test
    void testSingletonScope() {
        DatabaseUserRepository repo = new DatabaseUserRepository();
        container.registerSingleton(UserRepository.class, repo);
        UserRepository repo1 = container.getInstance(UserRepository.class);
        UserRepository repo2 = container.getInstance(UserRepository.class);
        assertSame(repo1, repo2);
    }

    @Test
    void testFactoryRegistration() {
        container.registerFactory(UserRepository.class, DatabaseUserRepository::new);
        UserRepository repo1 = container.getInstance(UserRepository.class);
        UserRepository repo2 = container.getInstance(UserRepository.class);
        assertNotSame(repo1, repo2);
        assertEquals(repo1.getClass(), repo2.getClass());
    }

    @Test
    void testConstructorInjection() {
        container.registerSingleton(UserRepository.class, new DatabaseUserRepository());
        UserService service = container.createInstance(UserService.class);
        assertNotNull(service);
        service.createUser("3", "Test User", "test@example.com");
        User user = service.getUser("3");
        assertNotNull(user);
        assertEquals("Test User", user.getName());
    }

    @Test
    void testUserRepository() {
        UserRepository repository = new DatabaseUserRepository();
        User user = new User("1", "Test User", "test@example.com");
        repository.save(user);
        User retrievedUser = repository.findById("1");
        assertNotNull(retrievedUser);
        assertEquals("Test User", retrievedUser.getName());
        assertEquals("test@example.com", retrievedUser.getEmail());
        List<User> allUsers = repository.findAll();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    @Test
    void testUserService() {
        UserRepository repository = new DatabaseUserRepository();
        UserService service = new UserService(repository);
        service.createUser("1", "Service User", "service@example.com");
        User user = service.getUser("1");
        assertNotNull(user);
        assertEquals("Service User", user.getName());
        List<User> allUsers = service.getAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    @Test
    void testUserModel() {
        User user = new User("123", "John Doe", "john@example.com");
        assertEquals("123", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        String userString = user.toString();
        assertTrue(userString.contains("123"));
        assertTrue(userString.contains("John Doe"));
        assertTrue(userString.contains("john@example.com"));
    }

    @Test
    void testFunctionalContainerBasics() {
        functionalContainer.register("String", () -> "Hello World");
        functionalContainer.register("Integer", () -> 42);
        String stringValue = (String) functionalContainer.resolve("String");
        Integer intValue = (Integer) functionalContainer.resolve("Integer");
        assertEquals("Hello World", stringValue);
        assertEquals(Integer.valueOf(42), intValue);
    }

    @Test
    void testContainerErrorHandling() {
        assertThrows(IllegalArgumentException.class, () -> container.getInstance(String.class));
        assertThrows(RuntimeException.class, () -> functionalContainer.resolve("NonExistent"));
    }

    @Test
    void testComplexDependencyGraph() {
        container.registerFactory(UserRepository.class, DatabaseUserRepository::new);
        UserService service1 = container.createInstance(UserService.class);
        UserService service2 = container.createInstance(UserService.class);
        assertNotNull(service1);
        assertNotNull(service2);
        service1.createUser("user1", "User One", "user1@example.com");
        service2.createUser("user2", "User Two", "user2@example.com");
        assertEquals("User One", service1.getUser("user1").getName());
        assertEquals("User Two", service2.getUser("user2").getName());
    }

    @Test
    void testServiceLifecycle() {
        UserRepository repository = new DatabaseUserRepository();
        UserService service = new UserService(repository);
        service.createUser("1", "User 1", "user1@example.com");
        service.createUser("2", "User 2", "user2@example.com");
        service.createUser("3", "User 3", "user3@example.com");
        assertEquals(3, service.getAllUsers().size());
    }

    @Test
    void testEquivalenceAcrossApproaches() {
        container.registerSingleton(UserRepository.class, new DatabaseUserRepository());
        UserService traditionalService = container.createInstance(UserService.class);
        functionalContainer.register("UserRepository", DatabaseUserRepository::new);
        functionalContainer.register("UserService", () -> new UserService(
                (UserRepository) functionalContainer.resolve("UserRepository")));
        UserService functionalService = (UserService) functionalContainer.resolve("UserService");
        traditionalService.createUser("traditional", "Traditional User", "traditional@example.com");
        functionalService.createUser("functional", "Functional User", "functional@example.com");
        assertEquals("Traditional User", traditionalService.getUser("traditional").getName());
        assertEquals("Functional User", functionalService.getUser("functional").getName());
    }

    @Test
    void testMultipleServiceInstances() {
        UserRepository sharedRepo = new DatabaseUserRepository();
        container.registerSingleton(UserRepository.class, sharedRepo);
        UserService service1 = container.createInstance(UserService.class);
        UserService service2 = container.createInstance(UserService.class);
        service1.createUser("shared1", "Shared User 1", "shared1@example.com");
        service2.createUser("shared2", "Shared User 2", "shared2@example.com");
        assertTrue(service1.getAllUsers().size() >= 2);
        assertEquals(service1.getAllUsers().size(), service2.getAllUsers().size());
    }

    @Test
    void testContainerRegistrationLogging() {
        assertDoesNotThrow(() -> {
            container.registerSingleton(UserRepository.class, new DatabaseUserRepository());
            container.registerFactory(String.class, () -> "Test String");
        });
        assertDoesNotThrow(() -> functionalContainer.register("TestKey", () -> "Test Value"));
    }
}