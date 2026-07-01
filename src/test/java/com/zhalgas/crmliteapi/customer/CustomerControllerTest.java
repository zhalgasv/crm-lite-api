package com.zhalgas.crmliteapi.customer;


import com.zhalgas.crmliteapi.customer.dto.CustomerRequest;
import com.zhalgas.crmliteapi.customer.dto.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;
import com.zhalgas.crmliteapi.exception.ResourceNotFoundException;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;


import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetCustomerById() throws Exception {
        CustomerResponse response = new CustomerResponse(
                1L,
                "Adil",
                "adil@test.com",
                "7777777777",
                "TestCompany",
                CustomerStatus.NEW,
                1L,
                "manager",
                LocalDateTime.now()
        );
        when(customerService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Adil"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    void shouldGetAllCustomer() throws Exception {
        CustomerResponse response1 = new CustomerResponse(
                1L,
                "adil",
                "adil@test.com",
                "777777777777",
                "company",
                CustomerStatus.NEW,
                1L,
                "manager",
                LocalDateTime.now()
        );

        CustomerResponse response2 = new CustomerResponse(
                2L,
                "John",
                "john@test.com",
                "777777777777",
                "company",
                 CustomerStatus.NEW,
                2L,
                "manager",
                LocalDateTime.now()
        );

        when(customerService.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("adil"))
                .andExpect(jsonPath("$[1].name").value("John"));
    }

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest(
                "adil",
                "adil@test.com",
                "77777777777",
                "company",
                CustomerStatus.NEW,
                1L
        );

        CustomerResponse response = new CustomerResponse(
                1L,
                "adil",
                "adil@test.com",
                "7777777777777",
                "company",
                CustomerStatus.NEW,
                1L,
                "manager",
                LocalDateTime.now()
        );

        when(customerService.create(any(CustomerRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("adil"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }


    @Test
    void shouldUpdateCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest(
                "Adil Updated",
                "adil@test.com",
                "77777777777",
                "UpdatedCompany",
                CustomerStatus.ACTIVE,
                1L
        );

        CustomerResponse response = new CustomerResponse(
                1L,
                "Adil Updated",
                "adilt@test.com",
                "77777777777",
                "UpdatedCompany",
                CustomerStatus.ACTIVE,
                1L,
                "manager",
                LocalDateTime.now()
        );

        when(customerService.update(1L, request)).thenReturn(response);

        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Adil Updated"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        doNothing().when(customerService).deleteById(1L);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenCustomerNotFound() throws Exception {
        when(customerService.findById(99L))
                .thenThrow(new ResourceNotFoundException("Customer id not found: 99"));

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Customer id not found: 99"));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        CustomerRequest request = new CustomerRequest(
                "",
                "wrong-email",
                "7777777777",
                "TestCompany",
                null,
                null
        );

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.ownerId").exists());
    }
}
