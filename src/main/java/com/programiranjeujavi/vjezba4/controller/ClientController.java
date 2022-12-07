package com.programiranjeujavi.vjezba4.controller;

import com.programiranjeujavi.vjezba4.data.ClientDto;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long clientId) {
        var client = clientRepository.findById(clientId);

        if (client.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var clientDto = modelMapper.map(client, ClientDto.class);

        return ResponseEntity.ok().body(clientDto);
    }
}
