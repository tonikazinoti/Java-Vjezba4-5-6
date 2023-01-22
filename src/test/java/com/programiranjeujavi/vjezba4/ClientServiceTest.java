package com.programiranjeujavi.vjezba4;

import com.programiranjeujavi.vjezba4.data.ClientDto;
import com.programiranjeujavi.vjezba4.data.ClientPostDto;
import com.programiranjeujavi.vjezba4.entity.Address;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.exception.BadRequestException;
import com.programiranjeujavi.vjezba4.repository.AddressRepository;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import com.programiranjeujavi.vjezba4.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private Address address;

    @BeforeAll
    static void setupAll() {

    }

    @BeforeEach
    public void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        address = Address.builder()
                .id(1L)
                .street("Dubrovačka 21")
                .city("Split")
                .country("Hrvatska")
                .zipCode("21000")
                .build();
        client = Client.builder()
                .id(1L)
                .firstName("Toni")
                .lastName("Kazinoti")
                .address(address)
                .build();
    }

    @Test
    public void givenValidClient_whenCreateClient_thenCreateThatClient() throws BadRequestException {
        lenient().when(clientRepository.findById(client.getId()))
                .thenReturn(Optional.empty());

        lenient().when(clientRepository.save(client)).thenReturn(client);

        ModelMapper realModelMapper = new ModelMapper();
        ClientPostDto clientPostDto = realModelMapper.map(client, ClientPostDto.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<ClientPostDto> response = clientService.createClient(clientPostDto, bindingResult);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.hasBody()).isTrue();
    }

    @Test
    public void givenClientId_whenGetClientById_thenReturnCorrectClient(){
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ResponseEntity<ClientDto> response = clientService.getClientById(client.getId());

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasBody()).isTrue();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(1);
    }
}
