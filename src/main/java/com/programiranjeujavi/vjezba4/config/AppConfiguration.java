package com.programiranjeujavi.vjezba4.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.programiranjeujavi.vjezba4.data.ClientPostDto;
import com.programiranjeujavi.vjezba4.entity.Address;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.entity.Device;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class AppConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner initDatabase(
            ClientRepository clientRepository) {
        return args -> {
            try {
                var clientsJson = Files.readString(Paths.get("clients.json"));

                var gson = new Gson();
                Type ClientsListType = new TypeToken<List<ClientPostDto>>(){}.getType();
                List<ClientPostDto> clientsToInsert = gson.fromJson(clientsJson, ClientsListType);

                clientsToInsert.forEach(clientDto -> {
                    var address = Address.builder()
                            .street(clientDto.getAddress().getStreet())
                            .city(clientDto.getAddress().getCity())
                            .country(clientDto.getAddress().getCountry())
                            .zipCode(clientDto.getAddress().getZipCode())
                            .build();
                    var device = Device.builder()
                            .modelName(clientDto.getDevice().getModelName())
                            .build();
                    var client = Client.builder()
                            .address(address)
                            .device(device)
                            .firstName(clientDto.getFirstName())
                            .lastName(clientDto.getLastName())
                            .build();
                    clientRepository.save(client);
                });

            } catch(Exception e) {
                e.printStackTrace();
            }

        };
    }
}
