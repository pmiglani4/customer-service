package com.sidecar.customer.service.impl;

import com.sidecar.customer.Mapper.CustomerMapper;
import com.sidecar.customer.domain.Customer;
import com.sidecar.customer.dto.request.CustomerRequestDto;
import com.sidecar.customer.exceptions.CustomerNotFoundException;
import com.sidecar.customer.repository.CustomerRepository;
import com.sidecar.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerRequestDto.CustomerDto> getCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerRequestDto.CustomerDto getCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(!customerOptional.isPresent()){
            throw new CustomerNotFoundException(String.format("Customer with id %s is not present in DB",customerId));
        }
        return CustomerMapper.mapToCustomerDto(customerOptional.get());
    }

    @Override
    public CustomerRequestDto.CustomerDto createCustomer(CustomerRequestDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        return CustomerMapper.mapToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public CustomerRequestDto.CustomerDto updateCustomer(Long customerId, CustomerRequestDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(!customerOptional.isPresent()){
            throw new CustomerNotFoundException(String.format("Customer with id %s is not present in DB",customerId));
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        customer.setId(customerId);
        return CustomerMapper.mapToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(!customerOptional.isPresent()){
            throw new CustomerNotFoundException(String.format("Customer with id %s is not present in DB",customerId));
        }
        customerRepository.delete(customerOptional.get());
    }
}
