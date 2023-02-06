package com.example.initiativeservice.controller;

import com.example.initiativeservice.service.InitiativeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.example.initiativeservice.config.TestData.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class InitiativeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InitiativeService initiativeService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void giveInitiativeRequest_shouldReturn200Ok() throws Exception {
        when(initiativeService.create(initiativeRequest)).thenReturn(1L);
        mockMvc.
                perform(post("/v1/initiatives")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initiativeRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    public void giveInitiativeUpdateRequest_shouldReturn200Ok() throws Exception {
        doNothing().when(initiativeService).update(initiativeRequest,1L);
        mockMvc.
                perform(put("/v1/initiatives/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initiativeRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllInitiatives() throws Exception {
        when(initiativeService.getAll()).thenReturn(List.of(initiativeResponse));
        mockMvc
                .perform(get("/v1/initiatives").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(initiativeResponse))));
    }

    @Test
    public void givenAnId_shouldReturnInitiatives() throws Exception {
        when(initiativeService.get(1)).thenReturn(initiativeResponse);
        mockMvc
                .perform(get("/v1/initiatives/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(initiativeResponse)));
    }

    @Test
    public void givenListOfIds_shouldReturnInitiativeMapMatchingIds() throws Exception {
        Long[] ids = {1L,2L};
        when(initiativeService.getByIds(ids)).thenReturn(initiativeResponseMap);
        mockMvc.perform(get("/v1/initiatives/byIds").contentType(MediaType.APPLICATION_JSON).queryParam("ids","1","2"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(initiativeResponseMap)));

    }



}
