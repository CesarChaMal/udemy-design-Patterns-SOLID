package com.balazsholczer.anticorruption;

public class LegacyCustomer {
    public String cust_id;
    public String full_name;
    public String email_addr;
    public String phone_num;
    public int status_code; // 1=active, 0=inactive
    
    public LegacyCustomer(String cust_id, String full_name, String email_addr, String phone_num, int status_code) {
        this.cust_id = cust_id;
        this.full_name = full_name;
        this.email_addr = email_addr;
        this.phone_num = phone_num;
        this.status_code = status_code;
    }
    
    @Override
    public String toString() {
        return "LegacyCustomer{cust_id='" + cust_id + "', full_name='" + full_name + 
               "', email_addr='" + email_addr + "', phone_num='" + phone_num + 
               "', status_code=" + status_code + "}";
    }
}