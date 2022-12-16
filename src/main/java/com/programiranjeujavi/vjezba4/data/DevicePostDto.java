package com.programiranjeujavi.vjezba4.data;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevicePostDto {
    @Pattern(regexp = "^([A-z0-9-]+\\s)*[A-z0-9-]+$", message = "Model name not in a proper format")
    private String modelName;
}
