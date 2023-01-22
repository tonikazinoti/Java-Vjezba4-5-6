package com.programiranjeujavi.vjezba4.service;

import com.programiranjeujavi.vjezba4.data.ClientDto;
import com.programiranjeujavi.vjezba4.data.ClientPostDto;
import com.programiranjeujavi.vjezba4.data.DeviceDto;
import com.programiranjeujavi.vjezba4.data.DevicePostDto;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.entity.Device;
import com.programiranjeujavi.vjezba4.exception.BadRequestException;
import com.programiranjeujavi.vjezba4.repository.AddressRepository;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import com.programiranjeujavi.vjezba4.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final DeviceRepository deviceRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ResponseEntity<List<ClientDto>> getAllClients() {
        var clients = clientRepository.findAll();

        List<ClientDto> clientsDto = modelMapper.map(clients, new TypeToken<List<ClientDto>>() {}.getType());

        return ResponseEntity.ok().body(clientsDto);
    }

    @Override
    public ResponseEntity<ClientDto> getClientById(Long clientId) {
        var client = clientRepository.findById(clientId);

        if (client.isEmpty()) {
            throw new EntityNotFoundException("Client with that id not found.");
        }

        var clientDto = modelMapper.map(client, ClientDto.class);

        return ResponseEntity.ok().body(clientDto);
    }

    @Override
    public ResponseEntity<DeviceDto> createDevice(Long clientId, DevicePostDto devicePostDto) {
        var client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            throw new EntityNotFoundException("Client with that id not found");
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
    public ResponseEntity<ClientPostDto> createClient(ClientPostDto clientPostDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }

        if (addressRepository.existsByStreetIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCaseAndZipCodeIgnoreCase(
                clientPostDto.getAddress().getStreet(),
                clientPostDto.getAddress().getCity(),
                clientPostDto.getAddress().getCountry(),
                clientPostDto.getAddress().getZipCode())) {
            throw new BadRequestException("Somebody already lives on that address");
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
