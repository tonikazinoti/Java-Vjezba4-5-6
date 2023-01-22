package com.javalab.clientapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javalab.clientapp.data.DeviceReadingPostDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface DeviceService {
    void setAddDeviceReadingPageModel(Model model) throws JsonProcessingException;

    boolean tryAddDeviceReading(Model model, BindingResult bindingResult, DeviceReadingPostDto deviceReadingPostDto) throws JsonProcessingException;
}
