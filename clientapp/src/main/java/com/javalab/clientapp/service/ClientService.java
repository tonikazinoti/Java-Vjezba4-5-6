package com.javalab.clientapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ui.Model;

public interface ClientService {
    void setGetAllClientsPageModel(Model model) throws JsonProcessingException;

    boolean setGetClientByIdPageModel(Model model, Long clientId, Long pageNumber) throws JsonProcessingException;
}
