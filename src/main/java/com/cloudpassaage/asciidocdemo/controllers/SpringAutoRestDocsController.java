package com.cloudpassaage.asciidocdemo.controllers;

import com.cloudpassaage.asciidocdemo.dto.Address;
import com.cloudpassaage.asciidocdemo.dto.Country;
import com.cloudpassaage.asciidocdemo.dto.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringAutoRestDocsController {

    /**
     * Returns Customer Object by id.
     * @param id the identifier of the customer in customer table
     */
    @GetMapping("v2/customer/{id}")
    public Customer getCustomerById(@PathVariable(value = "id", required = false) Long id) {
        return Customer.builder()
                .name("Nick").surname("Andrew").age(12).mail("test@mail.ru")
                .address(Address.builder().city("Almaty").country(Country.KAZAKHSTAN).build()).build();
    }

    /**
     * Creates customer
     * @param customer entity which will be persisted
     */
    @PostMapping("v2/customer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customer;
    }
}
