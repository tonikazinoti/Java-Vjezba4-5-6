package com.programiranjeujavi.vjezba4.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressPostDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?<name>\\w[\\s\\w]+?)\\s*(?<num>\\d+\\s*[a-z]?)$", message = "City is not in a correct format")
    private String street;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^([A-z]+\\s)*[A-z]+$", message = "City is not in a correct format")
    private String city;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^([A-z]+\\s)*[A-z]+$", message = "Country is not in a correct format")
    private String country;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Zip Code is not in a correct format")
    private String zipCode;
}
