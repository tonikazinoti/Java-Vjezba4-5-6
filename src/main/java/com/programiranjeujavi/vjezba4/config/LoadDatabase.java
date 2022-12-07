package com.programiranjeujavi.vjezba4.config;

import com.programiranjeujavi.vjezba4.entity.Address;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.repository.AddressRepository;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(
            ClientRepository clientRepository) {
        return args -> {
            //TODO load json file, insert clients with addresses
            var address = Address.builder()
                            .street("Narodnog Preporoda 41")
                            .city("Kaštel Novi")
                            .country("Croatia")
                            .zipCode("21216")
                            .build();
            var client = Client.builder()
                    .firstName("Toni")
                    .lastName("Kazinoti")
                    .address(address)
                    .build();

            clientRepository.save(client);
        };
    }
}
