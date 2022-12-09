package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    ResponseEntity<List<DeviceReadingDto>> getAllReadingByDeviceId(Long deviceId);

    ResponseEntity<DeviceReadingDto> createDeviceReading(Long deviceId, Integer year, Short month);

    ResponseEntity<?> searchDeviceReadings(Long deviceId, Integer year, Optional<Short> month, Optional<Boolean> showTotal);
}
