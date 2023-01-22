package com.javalab.clientapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalab.clientapp.service.ClientService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RequestMapping("/clients")
@Controller
public class ClientController {
    private final RestTemplate restTemplate;
    private final ClientService clientService;

    @GetMapping
    public String getAllClients(Model model) throws JsonProcessingException {
        clientService.setGetAllClientsPageModel(model);

        return "client/client-list";
    }

    @GetMapping("/{clientId}")
    public String getClientById(@PathVariable @NotNull Long clientId, @RequestParam(name = "page", required = true, defaultValue = "1") Long pageNumber, Model model) throws JsonProcessingException {
        var isSuccessful = clientService.setGetClientByIdPageModel(model, clientId, pageNumber);

        if (!isSuccessful) {
            return "redirect:/clients";
        }

        return "client/details";
    }
}
