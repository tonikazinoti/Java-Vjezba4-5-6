package com.programiranjeujavi.vjezba4.controller;

import com.programiranjeujavi.vjezba4.data.ClientDto;
import com.programiranjeujavi.vjezba4.data.ClientPostDto;
import com.programiranjeujavi.vjezba4.data.DeviceDto;
import com.programiranjeujavi.vjezba4.data.DevicePostDto;
import com.programiranjeujavi.vjezba4.service.ClientServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {
    private final ClientServiceImpl clientService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable @NotNull Long clientId) {
        return clientService.getClientById(clientId);
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientPostDto clientPostDto,
                                          BindingResult bindingResult) {
        return clientService.createClient(clientPostDto, bindingResult);
    }

    @PostMapping("/{clientId}/device")
    public ResponseEntity<DeviceDto> createDevice(@PathVariable @NotNull Long clientId,
                                                  @RequestBody DevicePostDto devicePostDto) {
        return clientService.createDevice(clientId, devicePostDto);
    }
}
