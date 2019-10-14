package com.cloudpassaage.asciidocdemo.controllers;

import com.cloudpassaage.asciidocdemo.dto.Address;
import com.cloudpassaage.asciidocdemo.dto.Country;
import com.cloudpassaage.asciidocdemo.dto.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsciiDocController {

    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable("id") Long id) {
        System.out.println("------------- CALLING ----------------");
        Address addres = Address.builder().city("Minsk").country(Country.BELARUS).build();
        return Customer.builder()
                .name("Петр").surname("Петров")
                .age(id.intValue()).address(addres).mail("dasdsa@gmail.com").build();
    }
}
