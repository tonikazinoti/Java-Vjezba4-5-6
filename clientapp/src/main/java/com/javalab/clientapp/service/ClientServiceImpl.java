package com.javalab.clientapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final RestTemplate restTemplate;

    @Override
    public void setGetAllClientsPageModel(Model model) throws JsonProcessingException {
        String url = "http://localhost:9090/clients";
        var jsonString = this.restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        var clients = objectMapper.readValue(jsonString, List.class);

        model.addAttribute("clients", clients);
    }

    @Override
    public boolean setGetClientByIdPageModel(Model model, Long clientId, Long pageNumber) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String clientUrl = "http://localhost:9090/clients/" + clientId;
        var clientJsonString = "";
        try {
            clientJsonString = this.restTemplate.getForObject(clientUrl, String.class);
        } catch (HttpClientErrorException ex) {
            return false;
        }

        var client = objectMapper.readValue(clientJsonString, Map.class);

        Map device = (Map) client.get("device");
        int deviceId = (int) device.get("id");
        String readingsUrl = "http://localhost:9090/devices/" + deviceId + "/readings" + "?page=" + pageNumber;
        var readingsJsonString = this.restTemplate.getForObject(readingsUrl, String.class);
        var readings = objectMapper.readValue(readingsJsonString, List.class);

        model.addAttribute("client", client);
        model.addAttribute("readings", readings);
        return true;
    }
}
