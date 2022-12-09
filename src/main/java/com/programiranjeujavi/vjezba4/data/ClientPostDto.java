package com.programiranjeujavi.vjezba4.data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^([A-z]+\\s)*[A-z]+$", message = "First name not in a proper format")
    private String firstName;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^([A-z]+\\s)*[A-z]+$", message = "Last name not in a proper format")
    private String lastName;
    @Valid
    private AddressPostDto address;
    @Valid
    private DevicePostDto device;
}
