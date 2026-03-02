package com.georgiia.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "customeraddresses")
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street_address   ;
    private String postal_code;
    private String city;
    private String country;


    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    public CustomerAddress() {}

    public CustomerAddress(String street_address, String city,String postal_code ,  String country) {
        this.street_address = street_address;
        this.postal_code = postal_code;
        this.city = city;
        this.country = country;
    }

    // getters и setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStreetAddress() { return street_address; }
    public void setStreetAddress(String street_address) { this.street_address = street_address; }

    public String getPostalCode() { return postal_code; }
    public void setPostalCode(String postal_code) { this.postal_code = postal_code; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }


    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }


    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}