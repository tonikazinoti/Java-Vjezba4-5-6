package com.programiranjeujavi.vjezba4.controller;

import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import com.programiranjeujavi.vjezba4.data.DeviceReadingUpdateDto;
import com.programiranjeujavi.vjezba4.exception.BadRequestException;
import com.programiranjeujavi.vjezba4.service.DeviceReadingService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/device-readings")
public class DeviceReadingController {
    private final DeviceReadingService deviceReadingService;

    @PutMapping("/{deviceReadingId}")
    ResponseEntity<DeviceReadingDto> updateDeviceReading(@PathVariable @NotNull Long deviceReadingId,
                                                         @RequestBody DeviceReadingUpdateDto deviceReadingUpdateDto,
                                                         BindingResult bindingResult) throws BadRequestException {
        return deviceReadingService.updateDeviceReading(deviceReadingId, deviceReadingUpdateDto, bindingResult);
    }

    @DeleteMapping("/{deviceReadingId}")
    ResponseEntity<DeviceReadingDto> deleteDeviceReading(@PathVariable @NotNull Long deviceReadingId) {
        return deviceReadingService.deleteDeviceReading(deviceReadingId);
    }
}
