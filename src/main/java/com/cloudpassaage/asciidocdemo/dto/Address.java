package com.cloudpassaage.asciidocdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    /** country of the customer*/
    private Country country;
    /** city of the customer*/
    private String city;
}
