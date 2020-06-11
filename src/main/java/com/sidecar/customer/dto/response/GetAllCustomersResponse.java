package com.sidecar.customer.dto.response;

import com.sidecar.customer.dto.request.CustomerRequestDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCustomersResponse{
    private  Integer count;
    private List<CustomerRequestDto.CustomerDto> customers;

}
