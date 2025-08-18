package com.balazsholczer.businessdelegate;

public class BusinessDelegate {
    
    private final BusinessLookup lookupService;
    private BusinessService businessService;
    private String serviceType;
    
    public BusinessDelegate() {
        this.lookupService = new BusinessLookup();
    }
    
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    public String doTask(String request) {
        businessService = lookupService.getBusinessService(serviceType);
        return businessService.processRequest(request);
    }
}