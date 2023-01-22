package com.programiranjeujavi.vjezba4.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public
class ApiError {
    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status) {
        this.status = status;
    }
}