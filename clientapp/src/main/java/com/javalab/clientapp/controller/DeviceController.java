package com.javalab.clientapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalab.clientapp.data.DeviceReadingPostDto;
import com.javalab.clientapp.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;
    @GetMapping("/add-reading")
    public String getAddDeviceReadingPage(@Valid @ModelAttribute("reading") DeviceReadingPostDto deviceReadingPostDto, Model model) throws JsonProcessingException {
        deviceService.setAddDeviceReadingPageModel(model);

        return "device/add-reading";
    }

    @PostMapping("/add-reading")
    public String addDeviceReading(@Valid @ModelAttribute("reading") DeviceReadingPostDto deviceReadingPostDto, BindingResult bindingResult, Model model) throws JsonProcessingException {
        var isSuccessful = deviceService.tryAddDeviceReading(model, bindingResult, deviceReadingPostDto);

        if (!isSuccessful) {
            return "device/add-reading";
        }

        return "redirect:/clients";
    }
}
