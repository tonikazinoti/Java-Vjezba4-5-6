package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.DeviceDto;
import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import com.programiranjeujavi.vjezba4.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    ResponseEntity<List<DeviceReadingDto>> getAllReadingByDeviceId(Long deviceId, Long pageNumber);

    ResponseEntity<DeviceReadingDto> createDeviceReading(Long deviceId, Integer year, Short month) throws BadRequestException;

    ResponseEntity<?> searchDeviceReadings(Long deviceId, Integer year, Optional<Short> month, Optional<Boolean> showTotal, Long pageNumber) throws BadRequestException;
}
