package com.programiranjeujavi.vjezba4;

import com.programiranjeujavi.vjezba4.data.ClientDto;
import com.programiranjeujavi.vjezba4.entity.Client;
import com.programiranjeujavi.vjezba4.repository.ClientRepository;
import com.programiranjeujavi.vjezba4.service.ClientService;
import com.programiranjeujavi.vjezba4.service.ClientServiceImpl;
import com.programiranjeujavi.vjezba4.service.DeviceServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
class ClientControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientServiceImpl clientService;

	@MockBean
	private DeviceServiceImpl deviceService;

	@Test
	void whenGetClientById_withExistingId_shouldReturnCorrectClientWithStatusOk() throws Exception {
		var clientDto = new ClientDto();
		clientDto.setId(1L);
		clientDto.setFirstName("Toni");
		clientDto.setLastName("Kazinoti");
		when(clientService.getClientById(1L)).thenReturn(ResponseEntity.ok(clientDto));

		mockMvc.perform(get("/clients/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("{\"id\":1,\"firstName\":\"Toni\",\"lastName\":\"Kazinoti\",\"address\":null,\"device\":null}"));

		verify(clientService).getClientById(1L);
	}

	@Test
	void whenGetClientById_withNonExistingId_shouldThrowExceptionWithStatusNotFound() throws Exception {
		when(clientService.getClientById(1L)).thenThrow(
				new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with that id not found"));

		mockMvc.perform(get("/clients/1"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(result -> assertThat(result.getResolvedException()).isNotNull())
				.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(
						ResponseStatusException.class))
				.andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage())
						.isEqualTo("404 NOT_FOUND \"Client with that id not found\""));

		verify(clientService).getClientById(1L);
	}
}
