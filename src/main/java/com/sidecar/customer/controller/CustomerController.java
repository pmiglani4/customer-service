package com.sidecar.customer.controller;

import com.sidecar.customer.dto.request.CustomerRequestDto;
import com.sidecar.customer.dto.response.CustomerResponseDto;
import com.sidecar.customer.dto.response.GetAllCustomersResponse;
import com.sidecar.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    public  CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<GetAllCustomersResponse> getCustomers(){
        List<CustomerRequestDto.CustomerDto> customers = customerService.getCustomers();
        GetAllCustomersResponse response= new GetAllCustomersResponse(customers.size(), customers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRequestDto.CustomerDto> getCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @PostMapping()
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customer){
        Long customerId = customerService.createCustomer(customer).getId();
        String resourceLocation = String.format("/api/v1/customers/%s", customerId);
        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setCustomerId(customerId);
        responseDto.setLocation(resourceLocation);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long customerId,
                                           @RequestBody CustomerRequestDto customerRequestDto){
        customerService.updateCustomer(customerId, customerRequestDto);
        String resourceLocation = String.format("/api/v1/customers/%s", customerId);
        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setCustomerId(customerId);
        responseDto.setLocation(resourceLocation);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
