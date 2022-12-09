package com.programiranjeujavi.vjezba4.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private HttpStatusCode httpStatus;
    private String errorMessage;
}
