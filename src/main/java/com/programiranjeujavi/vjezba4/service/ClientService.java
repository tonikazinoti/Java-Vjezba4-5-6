package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.ClientDto;
import com.programiranjeujavi.vjezba4.data.ClientPostDto;
import com.programiranjeujavi.vjezba4.data.DeviceDto;
import com.programiranjeujavi.vjezba4.data.DevicePostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClientService {
    ResponseEntity<ClientDto> getClientById(Long clientId);

    ResponseEntity<DeviceDto> createDevice(Long clientId, DevicePostDto devicePostDto);

    ResponseEntity<?> createClient(@RequestBody ClientPostDto clientPostDto, BindingResult bindingResult);
}
