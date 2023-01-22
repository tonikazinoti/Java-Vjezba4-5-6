package com.programiranjeujavi.vjezba4.controller;

import com.programiranjeujavi.vjezba4.data.DeviceDto;
import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import com.programiranjeujavi.vjezba4.exception.BadRequestException;
import com.programiranjeujavi.vjezba4.service.DeviceService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping("/{deviceId}/readings")
    public ResponseEntity<List<DeviceReadingDto>> getAllByDeviceId(@PathVariable @NotNull Long deviceId,
                                                                   @RequestParam(name = "page", required = true, defaultValue = "1") Long pageNumber) {
        return deviceService.getAllReadingByDeviceId(deviceId, pageNumber);
    }

    @GetMapping("/{deviceId}/readings/search")
    public ResponseEntity<?> searchDeviceReadings(@PathVariable @NotNull Long deviceId,
                                                   @RequestParam Integer year,
                                                   @RequestParam Optional<Short> month,
                                                   @RequestParam Optional<Boolean> showTotal,
                                                   @RequestParam(name = "page", required = true, defaultValue = "1") Long pageNumber) throws BadRequestException {
        return deviceService.searchDeviceReadings(deviceId, year, month, showTotal, pageNumber);
    }

    @PostMapping("/{deviceId}/readings")
    public ResponseEntity<DeviceReadingDto> createDeviceReading(@PathVariable @NotNull Long deviceId,
                                                                @RequestParam Integer year,
                                                                @RequestParam Short month) throws BadRequestException {
        return deviceService.createDeviceReading(deviceId, year, month);
    }
}
