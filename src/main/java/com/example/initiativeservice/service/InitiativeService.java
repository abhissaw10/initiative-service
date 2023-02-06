package com.example.initiativeservice.service;

import com.example.initiativeservice.entity.Dependency;
import com.example.initiativeservice.entity.Initiative;
import com.example.initiativeservice.model.InitiativeRequest;
import com.example.initiativeservice.model.InitiativeResponse;
import com.example.initiativeservice.model.JiraRequest;
import com.example.initiativeservice.model.JiraResponse;
import com.example.initiativeservice.repository.InitiativeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.initiativeservice.model.InitiativeRequest.toInitiative;

@Service
@RequiredArgsConstructor
@Log4j2
public class InitiativeService {
    private final InitiativeRepository initiativeRepository;
    private final JiraClient jiraClient;
    public Long create(InitiativeRequest initiativeRequest) {
        JiraResponse jiraResponse = null;
        try {
                jiraResponse = jiraClient.createJiraEpic(JiraRequest
                    .builder()
                    .name(initiativeRequest.getTitle())
                    .build());
        }catch (Exception e){
            log.error("Error encountered while creating structure on JIRA "+e);
        }
        Initiative requestEntity = toInitiative(initiativeRequest);
        if(jiraResponse!=null)
        requestEntity.setJiraId(jiraResponse.getId());
        Initiative initiative = initiativeRepository.save(requestEntity);
        return initiative.getId();
    }

    public void update(InitiativeRequest initiativeRequest, Long id) {
        Initiative initiative = toInitiative(initiativeRequest);
        initiative.setId(id);
        initiativeRepository.save(initiative);
    }

    public List<InitiativeResponse> getAll() {
        List<Initiative> initiativeList = initiativeRepository.findAll();
        return initiativeList
                .stream()
                .map(i->getDependencies(i))
                .collect(Collectors.toList());
    }

    public InitiativeResponse get(long id) {
        Optional<Initiative> initiativeOptional = initiativeRepository.findById(id);
        return getDependencies(initiativeOptional.get());
    }

    public Map<Long, InitiativeResponse> getByIds(Long[] initiativeIds) {
         List<Initiative> initiatives = initiativeRepository.findAllById(Arrays.stream(initiativeIds).collect(Collectors.toList()));

        return InitiativeResponse.extracted(initiatives);
    }

    /**
     * Recursively fetch the dependent initiatives
     * @param initiative
     * @return
     */
    private InitiativeResponse getDependencies(Initiative initiative){
        InitiativeResponse initiativeResponse = InitiativeResponse.toInitiativeResponse(initiative);
        List<Dependency> dependencies = initiative.getDependencies();
        if(dependencies!=null && dependencies.size() > 0) {
            List<InitiativeResponse> dependencyList = new ArrayList<>();
            for (Dependency dependency : dependencies) {
                Optional<Initiative> initiativeOptional = initiativeRepository.findById(dependency.getDependency());
                dependencyList.add(getDependencies(initiativeOptional.get()));
            }
            initiativeResponse.setDependencies(dependencyList);
        }
        return initiativeResponse;
    }
}
