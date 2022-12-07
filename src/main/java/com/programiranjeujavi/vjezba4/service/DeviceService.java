package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.repository.DeviceReadingRepository;
import com.programiranjeujavi.vjezba4.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceReadingRepository deviceReadingRepository;

}
