package com.georgiia.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.georgiia.demo.dto.CustomerDTO;
import com.georgiia.demo.entity.CompanyCustomer;
import com.georgiia.demo.entity.Customer;
import com.georgiia.demo.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(c -> new CustomerDTO(c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(), c.getCustomersPhone()))
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return new CustomerDTO(c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(), c.getCustomersPhone());
    }

    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setCustomersPhone(dto.getCustomersPhone());
        Customer saved = customerRepository.save(customer);
        return new CustomerDTO(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail(), saved.getCustomersPhone());
    }

    public CustomerDTO createCompanyCustomer(CompanyCustomer companyCustomer) {
        Customer saved = customerRepository.save(companyCustomer);
        return new CustomerDTO(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail(), saved.getCustomersPhone());
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setCustomersPhone(dto.getCustomersPhone());
        Customer updated = customerRepository.save(customer);
        return new CustomerDTO(updated.getId(), updated.getFirstName(), updated.getLastName(), updated.getEmail(), updated.getCustomersPhone());
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
