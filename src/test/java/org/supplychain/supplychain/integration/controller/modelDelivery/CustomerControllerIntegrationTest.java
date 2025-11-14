package org.supplychain.supplychain.integration.controller.modelDelivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.supplychain.supplychain.dto.modelDelivery.CustomerDto;
import org.supplychain.supplychain.model.Customer;
import org.supplychain.supplychain.repository.modelDelivery.CustomerRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();

        customer = new Customer();
        customer.setName("Integration Test");
        customer.setEmail("integration@example.com");
        customer.setAddress("Street 1");
        customer.setPhone("0612345678");

        customerRepository.save(customer);
    }

    @Test
    @DisplayName("Create Customer - Integration Test")
    void testCreateCustomer() throws Exception {
        CustomerDto dto = new CustomerDto();
        dto.setName("New Customer");
        dto.setEmail("new@example.com");
        dto.setAddress("123 New Street");
        dto.setPhone("0600000000");

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("New Customer"))
                .andExpect(jsonPath("$.data.email").value("new@example.com"))
                .andExpect(jsonPath("$.data.address").value("123 New Street"));
    }

    @Test
    @DisplayName("Get Customer by ID - Integration Test")
    void testGetCustomerById() throws Exception {
        mockMvc.perform(get("/api/customers/{id}", customer.getIdCustomer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Integration Test"))
                .andExpect(jsonPath("$.data.email").value("integration@example.com"))
                .andExpect(jsonPath("$.data.address").value("Street 1"));
    }

    @Test
    @DisplayName("Update Customer - Integration Test")
    void testUpdateCustomer() throws Exception {
        CustomerDto dto = new CustomerDto();
        dto.setName("Updated Name");
        dto.setEmail("updated@example.com");
        dto.setAddress("456 Updated Street");
        dto.setPhone("0622222222");

        mockMvc.perform(put("/api/customers/{id}", customer.getIdCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.email").value("updated@example.com"))
                .andExpect(jsonPath("$.data.address").value("456 Updated Street"));
    }

    @Test
    @DisplayName("Delete Customer - Integration Test")
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/{id}", customer.getIdCustomer()))
                .andExpect(status().isOk());
    }
}
