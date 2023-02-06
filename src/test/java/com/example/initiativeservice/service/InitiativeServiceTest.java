package com.example.initiativeservice.service;

import com.example.initiativeservice.config.TestData;
import com.example.initiativeservice.entity.Initiative;
import com.example.initiativeservice.model.InitiativeRequest;
import com.example.initiativeservice.model.InitiativeResponse;
import com.example.initiativeservice.model.JiraRequest;
import com.example.initiativeservice.model.JiraResponse;
import com.example.initiativeservice.repository.InitiativeRepository;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.initiativeservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class InitiativeServiceTest {

    @Mock
    InitiativeRepository repository;

    @Mock
    JiraClient jiraClient;

    @InjectMocks InitiativeService initiativeService;

    @Test
    public void givenInitiativeRequest_shouldCreateInitiative(){
        when(repository.save(any(Initiative.class))).thenReturn(initiative);
        Long id = initiativeService.create(initiativeRequest);
        assertThat(id).isNotNull();
    }

    @Test
    public void givenInitiativeRequest_shouldMakeACallToJiraAPI(){
        when(repository.save(any(Initiative.class))).thenReturn(initiative);
        when(jiraClient.createJiraEpic(
                JiraRequest
                        .builder()
                        .name(initiative.getTitle())
                        .build()))
                .thenReturn(JiraResponse.builder().id(1).build());
        Long id = initiativeService.create(initiativeRequest);
        verify(jiraClient,times(1)).createJiraEpic(
                JiraRequest
                .builder()
                .name(initiative.getTitle())
                .build());
        assertThat(id).isNotNull();
    }

    @Test
    public void givenRequest_shouldThrowFeignExceptionFromJIRA(){
        when(repository.save(any(Initiative.class))).thenReturn(initiative);
        when(jiraClient.createJiraEpic(
                JiraRequest
                        .builder()
                        .name(initiative.getTitle())
                        .build()))
                .thenThrow(FeignException.class);
        Long id = initiativeService.create(initiativeRequest);
        assertThat(id).isNotNull();
    }

    @Test
    public void givenInitiativeId_shouldReturnInitiative(){
        when(repository.findById(1L)).thenReturn(Optional.of(initiative));
        when(repository.findById(2L)).thenReturn(Optional.of(nestedInitiative));
        when(repository.findById(3L)).thenReturn(Optional.of(nestedInitiative2));
        InitiativeResponse response = initiativeService.get(2L);
        assertThat(response.getDependencies().size()).isEqualTo(2);
        assertThat(response.getDependencies().get(0).getTitle()).isEqualTo(TEST_TITLE_1);
        assertThat(response.getDependencies().get(1).getTitle()).isEqualTo(TEST_TITLE_3);
    }

    @Test
    public void givenInitiativeUpdate_shouldUpdateInitiativeInDB(){
        when(repository.save(initiative)).thenReturn(initiative);
        initiativeService.update(initiativeRequest, 1L);
        verify(repository,times(1)).save(initiative);
    }

    @Test
    public void givenRequest_shouldReturnAllInitiatives(){
        when(repository.findAll()).thenReturn(List.of(nestedInitiative));
        when(repository.findById(1L)).thenReturn(Optional.of(initiative));
        when(repository.findById(3L)).thenReturn(Optional.of(nestedInitiative2));
        List<InitiativeResponse> response = initiativeService.getAll();
        assertThat(response).isNotNull();
        assertThat(response.get(0).getDependencies().get(0).getTitle()).isEqualTo(TEST_TITLE_1);
    }

    @Test
    public void givenRequestWithInitiativeIds_shouldReturnAllInitiatives(){
        Long[] ids = {1L,2L};
        when(repository.findAllById(List.of(1L,2L))).thenReturn(initiatives);
        Map<Long,InitiativeResponse> response = initiativeService.getByIds(ids);
        assertThat(response).isNotNull();
        assertThat(response.get(1L).getTitle()).isEqualTo(TEST_TITLE_1);
        assertThat(response.get(1L).getStatus()).isEqualTo(TEST_STATUS);
        assertThat(response.get(2L).getTitle()).isEqualTo(TEST_TITLE_2);
    }

}
