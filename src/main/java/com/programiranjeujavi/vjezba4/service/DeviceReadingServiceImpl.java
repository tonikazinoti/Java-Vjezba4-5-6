package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.DeviceReadingDto;
import com.programiranjeujavi.vjezba4.data.DeviceReadingUpdateDto;
import com.programiranjeujavi.vjezba4.exception.BadRequestException;
import com.programiranjeujavi.vjezba4.repository.DeviceReadingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class DeviceReadingServiceImpl implements DeviceReadingService {
    private final DeviceReadingRepository deviceReadingRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<DeviceReadingDto> updateDeviceReading(Long deviceReadingId, DeviceReadingUpdateDto deviceReadingUpdateDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("No energy consumption entered");
        }

        var deviceReading = deviceReadingRepository.findById(deviceReadingId).orElse(null);

        if (deviceReading == null) {
            throw new EntityNotFoundException("Device Reading not found");
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
            throw new EntityNotFoundException("Device Reading not found");
        }

        deviceReadingRepository.delete(deviceReading);
        return ResponseEntity.noContent().build();
    }
}
