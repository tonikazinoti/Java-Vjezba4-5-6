package com.javalab.clientapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalab.clientapp.data.DeviceReadingPostDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final RestTemplate restTemplate;

    @Override
    public void setAddDeviceReadingPageModel(Model model) throws JsonProcessingException {
        if (!model.containsAttribute("reading")) {
            model.addAttribute("reading", new DeviceReadingPostDto());
        }

        String url = "http://localhost:9090/clients";
        var jsonString = this.restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        var clients = objectMapper.readValue(jsonString, List.class);

        model.addAttribute("clients", clients);
    }

    @Override
    public boolean tryAddDeviceReading(Model model, BindingResult bindingResult, DeviceReadingPostDto deviceReadingPostDto) throws JsonProcessingException {
        setAddDeviceReadingPageModel(model);

        if (bindingResult.hasErrors()) {
            return false;
        }

        String url = "http://localhost:9090/devices/" + deviceReadingPostDto.getDeviceId() +
                "/readings?year=" + deviceReadingPostDto.getTimePeriod().getYear() + "&month="
                + deviceReadingPostDto.getTimePeriod().getMonthValue();
        var request = new HttpEntity<String>("");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            restTemplate.postForObject(url, request, String.class);

            return true;
        } catch (HttpClientErrorException ex) {
            var msg = ex.getMessage();
            bindingResult.rejectValue("timePeriod", "error.reading", msg);

            return false;
        }
    }
}
