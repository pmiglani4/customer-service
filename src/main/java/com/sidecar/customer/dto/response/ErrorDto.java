package com.sidecar.customer.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto{

    private String errorMessage;
    private String errorCode;
    private HttpStatus status;
    private OffsetDateTime time;

}
