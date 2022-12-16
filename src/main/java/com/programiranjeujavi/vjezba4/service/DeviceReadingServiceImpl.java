package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import com.programiranjeujavi.vjezba4.data.DeviceReadingUpdateDto;
import com.programiranjeujavi.vjezba4.repository.DeviceReadingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class DeviceReadingServiceImpl implements DeviceReadingService {
    private final DeviceReadingRepository deviceReadingRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<DeviceReadingDto> updateDeviceReading(Long deviceReadingId, DeviceReadingUpdateDto deviceReadingUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No energy consumption entered");
        }

        var deviceReading = deviceReadingRepository.findById(deviceReadingId).orElse(null);

        if (deviceReading == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Device Reading not found");
        }

        deviceReading.setEnergyConsumptionKwh(deviceReadingUpdateDto.getEnergyConsumptionKwh());
        deviceReadingRepository.save(deviceReading);

        var deviceReadingDto = modelMapper.map(deviceReading, DeviceReadingDto.class);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<DeviceReadingDto> deleteDeviceReading(Long deviceReadingId) {
        var deviceReading = deviceReadingRepository.findById(deviceReadingId).orElse(null);

        if (deviceReading == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Device Reading not found");
        }

        deviceReadingRepository.delete(deviceReading);
        return ResponseEntity.noContent().build();
    }
}
