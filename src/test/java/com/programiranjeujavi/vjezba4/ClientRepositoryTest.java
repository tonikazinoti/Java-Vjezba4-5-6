package com.programiranjeujavi.vjezba4;

import com.programiranjeujavi.vjezba4.entity.Address;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.repository.AddressRepository;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = Vjezba4Application.class)
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSave() {
        Client clientToInsert = getClient();
        clientRepository.save(clientToInsert);
        Optional<Client> found = clientRepository.findById(clientToInsert.getId());
        assertThat(found.isPresent()).isTrue();
        Client clientFound = found.get();
        assertThat(clientFound.getId()).isEqualTo(clientToInsert.getId());
    }

    @Test
    public void testFindById() {
        Client employee = getClient();
        clientRepository.save(employee);
        Optional<Client> result = clientRepository.findById(employee.getId());
        assertThat(result.isPresent()).isTrue();
        Client client = result.get();
        assertThat(client.getId()).isEqualTo(client.getId());
    }

    @Test
    public void testDeleteById() {
        Client client = getClient();
        clientRepository.save(client);
        addressRepository.deleteById(client.getAddress().getId());
        client.setAddress(null);
        clientRepository.deleteById(client.getId());
        List<Client> result = clientRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }

    private Client getClient() {
        Address address = Address.builder()
                .id(1L)
                .street("Dubrovačka 21")
                .city("Split")
                .country("Hrvatska")
                .zipCode("21000")
                .build();
        return Client.builder()
                .id(1L)
                .firstName("Toni")
                .lastName("Kazinoti")
                .address(address)
                .build();
    }
}
