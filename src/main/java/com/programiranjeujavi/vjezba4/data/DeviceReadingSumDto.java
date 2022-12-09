package com.programiranjeujavi.vjezba4.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceReadingSumDto {
    public int energyConsumptionKwh;
    @JsonFormat(pattern="yyyy-MM")
    public LocalDate timePeriod;
}
