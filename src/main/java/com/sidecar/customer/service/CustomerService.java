package com.sidecar.customer.service;

import com.sidecar.customer.dto.request.CustomerRequestDto;

import java.util.List;

public interface CustomerService {

    List<CustomerRequestDto.CustomerDto> getCustomers();

    CustomerRequestDto.CustomerDto getCustomer(Long customerId);

    CustomerRequestDto.CustomerDto createCustomer(CustomerRequestDto customer);

    CustomerRequestDto.CustomerDto updateCustomer(Long customerId, CustomerRequestDto customer);

    void deleteCustomer(Long customerId);

}
