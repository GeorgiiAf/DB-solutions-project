package com.georgiia.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;



@Entity
@DiscriminatorValue("COMPANY")
public class CompanyCustomer extends Customer {

    private String companyName;
    private String vatNumber;

    public CompanyCustomer() {}

    public CompanyCustomer(String firstName, String lastName, String email, String customersPhone,
                           String companyName, String vatNumber) {
        super(firstName, lastName, email, customersPhone);
        this.companyName = companyName;
        this.vatNumber = vatNumber;
    }

    // геттеры и сеттеры
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getVatNumber() { return vatNumber; }
    public void setVatNumber(String vatNumber) { this.vatNumber = vatNumber; }
}