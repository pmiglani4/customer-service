package com.sidecar.customer.service.impl;

import com.sidecar.customer.domain.Customer;
import com.sidecar.customer.dto.request.CustomerRequestDto;
import com.sidecar.customer.exceptions.CustomerNotFoundException;
import com.sidecar.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void getCustomers() {

        List<Customer> customerList = setUpCustomerData();
        when(customerRepository.findAll())
                .thenReturn(customerList);

        List<CustomerRequestDto.CustomerDto> customerDtoList = this.customerService.getCustomers();

        Mockito.verify(this.customerRepository, times(1)).findAll();

        assertNotNull(customerDtoList);
        assertEquals(customerList.size(), customerDtoList.size());
        assertEquals(customerList.get(0).getName(), customerDtoList.get(0).getName());
    }

    @Test
    void getCustomerById() throws CustomerNotFoundException {

        Optional<Customer> customerObj = Optional.of(new Customer(1234L, "TestUser1", "test@gmail.com", "+1 423-909-2050"));

        when(customerRepository.findById(any())).thenReturn(customerObj);

        CustomerRequestDto.CustomerDto customerDto = this.customerService.getCustomer(1234L);
        verify(this.customerRepository).findById(any());

        assertNotNull(customerDto);
        assertEquals("TestUser1", customerDto.getName());
    }

    @Test
    void getCustomerByIdWhenNotExists() throws CustomerNotFoundException {

        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(CustomerNotFoundException.class, () -> this.customerService.getCustomer(2L));
    }

    @Test
    void updateCustomer() throws CustomerNotFoundException {

        Optional<Customer> customerObj = Optional.of(new Customer(2L, "TestUser", "test@gmail.com", "+1 423-909-2050"));

        Customer customerToBeSaved = new Customer(2L, "UpdatedUser", "test@gmail.com", "+1 423-909-2050");

        when(customerRepository.findById(2L)).thenReturn(customerObj);
        when(customerRepository.save(any())).thenReturn(customerToBeSaved);

        CustomerRequestDto customerDto = new CustomerRequestDto("UpdatedUser", "test@gmail.com", "+1 423-909-2050");
        CustomerRequestDto.CustomerDto returnedCustomer = this.customerService.updateCustomer(2L, customerDto);

        assertNotNull(returnedCustomer);
        assertEquals("UpdatedUser", returnedCustomer.getName());

    }

    @Test
    void updateCustomerWhenCustomerNotExists() {

        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        assertThrows(CustomerNotFoundException.class, () -> {
            this.customerService.updateCustomer(50L, new CustomerRequestDto("UpdatedUser", "test@gmail.com", "+1 423-909-2050"));
        });

    }

    @Test
    void saveCustomer() {

        Customer customer = new Customer(2L, "NewTestUser", "test@gmail.com", "+1 423-909-2050");

        CustomerRequestDto customerReqDto = new CustomerRequestDto("NewTestUser", "test@gmail.com", "+1 423-909-2050");
        when(this.customerRepository.save(any())).thenReturn(customer);

        CustomerRequestDto.CustomerDto savedCustomerDto = this.customerService.createCustomer(customerReqDto);
        assertEquals(savedCustomerDto.getName(), customerReqDto.getName());
        assertEquals(savedCustomerDto.getEmailId(), customerReqDto.getEmailId());
        assertEquals(savedCustomerDto.getPhoneNumber(), customerReqDto.getPhoneNumber());
    }

    @Test
    void deleteCustomer() {
        Customer customerToBeDeleted = new Customer(2L, "TestUser", "test@gmail.com", "+1 423-909-2050");
        when(customerRepository.findById(any())).thenReturn(Optional.of(customerToBeDeleted));
        doNothing().when(this.customerRepository).delete(customerToBeDeleted);

        this.customerService.deleteCustomer(1L);
        verify(this.customerRepository).delete(customerToBeDeleted);
    }

    @Test
    void deleteCustomerWhenCustomerNotExists() {
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(CustomerNotFoundException.class, () -> this.customerService.deleteCustomer(1L));
    }

    public List<Customer> setUpCustomerData() {

        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1234L, "TestUser1", "test@gmail.com", "+1 423-909-2050"));

        customerList.add(new Customer(1234L, "TestUser2", "test2@gmail.com", "+1 423-909-2090"));

        return customerList;
    }

}