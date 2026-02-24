package com.georgiia.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
    private String customersPhone;


    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String customersPhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.customersPhone = customersPhone;
    }
    // getters ja setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    public String getCustomersPhone() { return customersPhone; }
    public void setCustomersPhone(String customersPhone) { this.customersPhone = customersPhone; }

}