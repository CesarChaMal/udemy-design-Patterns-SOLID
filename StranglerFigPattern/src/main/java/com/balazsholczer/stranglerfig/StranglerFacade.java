package com.balazsholczer.stranglerfig;

public class StranglerFacade {
    private final LegacySystem legacySystem;
    private final NewUserService newUserService;
    private boolean userServiceMigrated = false;
    
    public StranglerFacade() {
        this.legacySystem = new LegacySystem();
        this.newUserService = new NewUserService();
    }
    
    public String getUserData(String userId) {
        System.out.println("StranglerFacade: Routing user request for " + userId);
        
        if (userServiceMigrated) {
            System.out.println("StranglerFacade: Routing to new user service");
            return newUserService.getUserData(userId);
        } else {
            System.out.println("StranglerFacade: Routing to legacy system");
            return legacySystem.getUserData(userId);
        }
    }
    
    public String getOrderData(String orderId) {
        System.out.println("StranglerFacade: Routing order request for " + orderId);
        // Orders not migrated yet, always use legacy
        System.out.println("StranglerFacade: Routing to legacy system (not migrated)");
        return legacySystem.getOrderData(orderId);
    }
    
    public void migrateUserService() {
        System.out.println("StranglerFacade: Migrating user service to new implementation");
        userServiceMigrated = true;
        System.out.println("StranglerFacade: User service migration completed");
    }
    
    public void rollbackUserService() {
        System.out.println("StranglerFacade: Rolling back user service to legacy");
        userServiceMigrated = false;
        System.out.println("StranglerFacade: User service rollback completed");
    }
}