package com.sidecar.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidecar.customer.dto.request.CustomerRequestDto;
import com.sidecar.customer.exceptions.CustomerNotFoundException;
import com.sidecar.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = CustomerController.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CustomerControllerTest {

    private static final String MAPPING = "/api/v1/customers";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    CustomerRequestDto customerRequestDto;

    CustomerRequestDto.CustomerDto customerDto;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.customerRequestDto = new CustomerRequestDto("TestUser", "test@gmail.com", "+1 423-909-2050");
        this.customerDto = new CustomerRequestDto.CustomerDto(1234L, "TestUser", "test@gmail.com", "+1 423-909-2050");
    }

    @Test
    void getCustomerById() throws Exception {

        when(this.customerService.getCustomer(any()))
                .thenReturn(customerDto);

        this.mockMvc.perform
                (get(URI.create(MAPPING + "/" + customerDto.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(customerDto.getName())));

    }

    @Test
    void getCustomerById_whenCustomerNotPresent() throws Exception {

        when(this.customerService.getCustomer(any()))
                .thenThrow(new CustomerNotFoundException(String.format("Customer with id %s is not present in DB",customerDto.getId())));

        this.mockMvc.perform
                (get(URI.create(MAPPING + "/" + customerDto.getId())))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode", is("CUSTOMER_NOT_FOUND")));

    }

    @Test
    void getAllCustomers() throws Exception {

        List<CustomerRequestDto.CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList.add(this.customerDto);

        when(this.customerService.getCustomers())
                .thenReturn(customerDtoList);

        this.mockMvc.perform(get(URI.create(MAPPING)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count", is(customerDtoList.size())))
                .andExpect(jsonPath("$.customers[0].name", is(customerDtoList.get(0).getName())));
    }

    @Test
    void addNewCustomer() throws Exception {

        Long customerId = 123l;

        CustomerRequestDto customerReqDto = new CustomerRequestDto("TestUser", "test@gmail.com", "+1 423-909-2050");

        String requestBody = objectMapper.writeValueAsString(customerReqDto);

        CustomerRequestDto.CustomerDto savedCustomer = this.customerDto;
        when(this.customerService.createCustomer(any()))
                .thenReturn(savedCustomer);

        this.mockMvc.perform(post(MAPPING)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(jsonPath("$.location",is(MAPPING + "/"+ savedCustomer.getId())))
                .andExpect(status().isCreated());

    }

    @Test
    void updateCustomer() throws Exception {

        Long customerId = 1234l;

        CustomerRequestDto customerReqDto = new CustomerRequestDto("TestUser", "test@gmail.com", "+1 423-909-2050");

        String requestBody = objectMapper.writeValueAsString(customerReqDto);

        CustomerRequestDto.CustomerDto savedCustomer = customerDto;

        when(this.customerService.updateCustomer(any(), any()))
                .thenReturn(savedCustomer);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(MAPPING + "/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {

        doNothing().when(this.customerService).deleteCustomer(any());
        this.mockMvc.perform(delete(URI.create(MAPPING + "/123")))
                .andExpect(status().isNoContent());
    }
}