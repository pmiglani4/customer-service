package com.sidecar.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerRequestDto {
    private String name;
    private String emailId;
    private String phoneNumber;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerDto implements Serializable {

        private Long id;
        private String name;
        private String emailId;
        private String phoneNumber;

    }
}
