package com.balazsholczer.anticorruption;

public class CustomerAntiCorruptionLayer {
    private final LegacyCustomerService legacyService;
    
    public CustomerAntiCorruptionLayer(LegacyCustomerService legacyService) {
        this.legacyService = legacyService;
    }
    
    public ModernCustomer getCustomer(String customerId) {
        System.out.println("AntiCorruptionLayer: Translating request for customer " + customerId);
        
        // Call legacy system
        LegacyCustomer legacyCustomer = legacyService.getCustomerById(customerId);
        
        if (legacyCustomer == null) {
            return null;
        }
        
        // Translate legacy model to modern model
        return translateToModernCustomer(legacyCustomer);
    }
    
    public void updateCustomerStatus(String customerId, ModernCustomer.CustomerStatus status) {
        System.out.println("AntiCorruptionLayer: Translating status update for customer " + customerId);
        
        // Translate modern status to legacy status code
        int legacyStatusCode = translateToLegacyStatus(status);
        
        // Call legacy system
        legacyService.updateCustomerStatus(customerId, legacyStatusCode);
    }
    
    private ModernCustomer translateToModernCustomer(LegacyCustomer legacy) {
        System.out.println("AntiCorruptionLayer: Translating legacy customer to modern format");
        
        ModernCustomer.CustomerStatus status = legacy.status_code == 1 
            ? ModernCustomer.CustomerStatus.ACTIVE 
            : ModernCustomer.CustomerStatus.INACTIVE;
        
        return new ModernCustomer(
            legacy.cust_id,
            legacy.full_name,
            legacy.email_addr,
            legacy.phone_num,
            status
        );
    }
    
    private int translateToLegacyStatus(ModernCustomer.CustomerStatus status) {
        System.out.println("AntiCorruptionLayer: Translating modern status to legacy format");
        return status == ModernCustomer.CustomerStatus.ACTIVE ? 1 : 0;
    }
}