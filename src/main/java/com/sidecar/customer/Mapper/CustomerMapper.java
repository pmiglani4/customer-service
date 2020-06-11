package com.sidecar.customer.Mapper;

import com.sidecar.customer.domain.Customer;
import com.sidecar.customer.dto.request.CustomerRequestDto;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerRequestDto customerDto){
        Customer customer = new Customer();
        customer.setEmailId(customerDto.getEmailId());
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return  customer;
    }

    public static CustomerRequestDto.CustomerDto mapToCustomerDto(Customer customer){
        CustomerRequestDto.CustomerDto customerDto = new CustomerRequestDto.CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setEmailId(customer.getEmailId());
        customerDto.setName(customer.getName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }
}
