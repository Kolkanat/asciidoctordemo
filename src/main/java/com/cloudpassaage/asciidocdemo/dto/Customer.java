package com.cloudpassaage.asciidocdemo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Customer {
    /** name of the customer*/
    @NotBlank
    private String name;

    /** surname of the customer*/
    @NotBlank
    private String surname;

    /** age of the customer*/
    @NotNull
    @Min(0)
    @Max(130)
    private Integer age;

    /** address of the customer*/
    private Address address;

    /** email of the customer*/
    @Email
    private String mail;
}
