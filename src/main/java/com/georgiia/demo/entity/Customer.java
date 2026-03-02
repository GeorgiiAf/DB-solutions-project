package com.georgiia.demo.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "customer_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PERSON")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String customersPhone;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private CustomerAddress address;

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

    public CustomerAddress getAddress() { return address; }
    public void setAddress(CustomerAddress address) { this.address = address; }
}