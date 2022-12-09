package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.ClientDto;
import com.programiranjeujavi.vjezba4.data.ClientPostDto;
import com.programiranjeujavi.vjezba4.data.DeviceDto;
import com.programiranjeujavi.vjezba4.data.DevicePostDto;
import com.programiranjeujavi.vjezba4.entity.Address;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.entity.Device;
import com.programiranjeujavi.vjezba4.repository.AddressRepository;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import com.programiranjeujavi.vjezba4.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final DeviceRepository deviceRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ClientDto> getClientById(Long clientId) {
        var client = clientRepository.findById(clientId);

        if (client.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with that id not found");
        }

        var clientDto = modelMapper.map(client, ClientDto.class);

        return ResponseEntity.ok().body(clientDto);
    }

    @Override
    public ResponseEntity<DeviceDto> createDevice(Long clientId, DevicePostDto devicePostDto) {
        var client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with that id not found");
        }

        var deviceToInsert = Device.builder()
                .modelName(devicePostDto.getModelName())
                .client(client)
                .build();

        var device = deviceRepository.save(deviceToInsert);
        client.setDevice(deviceToInsert);
        clientRepository.save(client);

        var deviceDto = modelMapper.map(device, DeviceDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDto);
    }

    @Override
    @Transactional
    public ResponseEntity<?> createClient(ClientPostDto clientPostDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        }

        if (addressRepository.existsByStreetIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCaseAndZipCodeIgnoreCase(
                clientPostDto.getAddress().getStreet(),
                clientPostDto.getAddress().getCity(),
                clientPostDto.getAddress().getCountry(),
                clientPostDto.getAddress().getZipCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Somebody already lives on that address");
        }

        var client = modelMapper.map(clientPostDto, Client.class);
        if (client.getAddress() != null) {
            client.getAddress().setClient(null);
        }
        if (client.getDevice() != null) {
            client.getDevice().setClient(null);
        }

        clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientPostDto);
    }
}
