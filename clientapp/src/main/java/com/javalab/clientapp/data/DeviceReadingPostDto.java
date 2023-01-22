package com.javalab.clientapp.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceReadingPostDto {
    @NotNull
    private Long deviceId;
    @PastOrPresent
    private YearMonth timePeriod;
}
