package com.cloudpassaage.asciidocdemo;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.cloudpassaage.asciidocdemo.dto.Address;
import com.cloudpassaage.asciidocdemo.dto.Country;
import com.cloudpassaage.asciidocdemo.dto.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs
public class SpringAutoRestDocsControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(),
                        Preprocessors.preprocessResponse(
                                ResponseModifyingPreprocessors.replaceBinaryContent(),
                                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                Preprocessors.prettyPrint())))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section()))
                .build();
    }

    @Test
    public void getCustomerTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v2/customer/{id}", 13));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Nick")))
                .andExpect(jsonPath("surname", is("Andrew")));
    }

    @Test
    public void createCustomerTest() throws Exception {
        Customer customer = Customer.builder().name("Test").surname("Testov").age(12).mail("test@list.ru")
                .address(Address.builder().city("New York").country(Country.USA).build()).build();
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/v2/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
                        .contentType(MediaType.APPLICATION_JSON));

    }

}
