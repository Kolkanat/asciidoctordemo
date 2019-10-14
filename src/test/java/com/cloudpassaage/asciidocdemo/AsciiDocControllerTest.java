package com.cloudpassaage.asciidocdemo;

import com.cloudpassaage.asciidocdemo.dto.Address;
import com.cloudpassaage.asciidocdemo.dto.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class AsciiDocControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCustomerTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/customer/{id}", 13))
                    .andDo(document("asciidoccontroller/get-customer",
                        pathParameters(parameterWithName("id").description("Customer Id by which you get Customer object"))));

        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("Петр")));

        resultActions.andDo(
            document("asciidoccontroller/get-customer",
            responseFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of the Customer"),
                fieldWithPath("surname").type(JsonFieldType.STRING).description("Surname of the Customer"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("Age of the Customer"),
                fieldWithPath("mail").type(JsonFieldType.STRING).description("Mail of the Customer"),
                fieldWithPath("address").type(JsonFieldType.OBJECT).description("Address of the Customer"),
                fieldWithPath("address.country").type(JsonFieldType.STRING).description("Country of the Customer"),
                fieldWithPath("address.city").type(JsonFieldType.STRING).description("City of the Customer")
            )));
    }
}
