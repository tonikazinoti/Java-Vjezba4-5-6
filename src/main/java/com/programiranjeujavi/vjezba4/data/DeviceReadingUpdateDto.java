package com.programiranjeujavi.vjezba4.data;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceReadingUpdateDto {
    @NotNull
    public int energyConsumptionKwh;
}
