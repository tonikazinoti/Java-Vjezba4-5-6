package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import com.programiranjeujavi.vjezba4.data.DeviceReadingUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface DeviceReadingService {
    ResponseEntity<DeviceReadingDto> updateDeviceReading(Long deviceReadingId, DeviceReadingUpdateDto deviceReadingUpdateDto, BindingResult bindingResult);

    ResponseEntity<DeviceReadingDto> deleteDeviceReading(Long deviceReadingId);
}
