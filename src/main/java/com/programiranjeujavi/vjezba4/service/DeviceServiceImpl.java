package com.programiranjeujavi.vjezba4.service;

import com.google.gson.reflect.TypeToken;
import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import com.programiranjeujavi.vjezba4.data.DeviceReadingSumDto;
import com.programiranjeujavi.vjezba4.entity.DeviceReading;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import com.programiranjeujavi.vjezba4.repository.DeviceReadingRepository;
import com.programiranjeujavi.vjezba4.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final static int MIN_KWH_PER_MONTH = 0;
    private final static int MAX_KWH_PER_MONTH = 2000;
    private final DeviceRepository deviceRepository;
    private final DeviceReadingRepository deviceReadingRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<DeviceReadingDto>> getAllReadingByDeviceId(Long deviceId) {
        var device = deviceRepository.findById(deviceId).orElse(null);

        if (device == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Device with that id not found");
        }

        var deviceReadings = device.getDeviceReadings();

        List<DeviceReadingDto> deviceReadingsDto
                = modelMapper.map(deviceReadings, new TypeToken<List<DeviceReadingDto>>() {}.getType());

        return ResponseEntity.ok(deviceReadingsDto);
    }

    @Override
    public ResponseEntity<DeviceReadingDto> createDeviceReading(Long deviceId, Integer year, Short month) {
        var device = deviceRepository.findById(deviceId).orElse(null);
        if (device == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Device does not exist");
        }

        YearMonth yearMonth = null;
        try {
            yearMonth = YearMonth.of(year, month);
        } catch(DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid year or month");
        }

        var dateNow =  LocalDate.now();
        var timePeriodMonthStart = yearMonth.atDay(1);

        if (dateNow.isBefore(timePeriodMonthStart)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Year and month is in the future");
        }

        var timePeriodMonthEnd = yearMonth.atEndOfMonth();
        var existingReadingsForTimePeriod =
                deviceReadingRepository.findDeviceReadingByDeviceAndTimePeriodBetweenOrderByTimePeriodAsc(
                        device, timePeriodMonthStart, timePeriodMonthEnd);

        if (existingReadingsForTimePeriod.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entry already exists for that time period");
        }

        var energyConsumption = MIN_KWH_PER_MONTH + (int)(Math.random() * ((MAX_KWH_PER_MONTH - MIN_KWH_PER_MONTH) + 1));

        var deviceReadingToInsert = DeviceReading.builder()
                .energyConsumptionKwh(energyConsumption)
                .timePeriod(timePeriodMonthStart)
                .device(device)
                .build();

        var deviceReading = deviceReadingRepository.save(deviceReadingToInsert);

        var deviceReadingDto = modelMapper.map(deviceReading, DeviceReadingDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceReadingDto);
    }

    @Override
    public ResponseEntity<?> searchDeviceReadings(Long deviceId, Integer year, Optional<Short> month, Optional<Boolean> showTotal) {
        var device = deviceRepository.findById(deviceId).orElse(null);
        if (device == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Device does not exist");
        }

        var timePeriodStart = YearMonth.of(year, month.isPresent() ? month.get() : Month.JANUARY.getValue()).atDay(1);
        var timePeriodEnd = YearMonth.of(year, month.isPresent() ? month.get() : Month.DECEMBER.getValue()).atDay(1);

        if (showTotal.isPresent() && showTotal.get()) {
            var totalEnergyConsumption =
            deviceReadingRepository.findDeviceReadingByDeviceAndTimePeriodBetweenAndSumEnergyConsumption(
                    device, timePeriodStart, timePeriodEnd);
            if (totalEnergyConsumption == null) {
                totalEnergyConsumption = 0;
            }
            var deviceReadingDto = new DeviceReadingSumDto();
            deviceReadingDto.setEnergyConsumptionKwh(totalEnergyConsumption);
            deviceReadingDto.setTimePeriodStart(timePeriodStart);
            deviceReadingDto.setTimePeriodEnd(timePeriodEnd);
            return ResponseEntity.ok(deviceReadingDto);
        }

        var searchEntries =
                deviceReadingRepository.findDeviceReadingByDeviceAndTimePeriodBetweenOrderByTimePeriodAsc(
                        device, timePeriodStart, timePeriodEnd);

        List<DeviceReadingDto> deviceReadingsDto
                = modelMapper.map(searchEntries, new TypeToken<List<DeviceReadingDto>>() {}.getType());

        return ResponseEntity.ok(deviceReadingsDto);
    }
}
