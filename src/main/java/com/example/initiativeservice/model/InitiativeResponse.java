package com.example.initiativeservice.model;

import com.example.initiativeservice.entity.Initiative;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class InitiativeResponse {
    private Long id;
    private String title;
    private String successCriteria;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Integer jiraId;
    private List<InitiativeResponse> dependencies;

    public static InitiativeResponse toInitiativeResponse(Initiative initiative){
        return InitiativeResponse
                .builder()
                .id(initiative.getId())
                .title(initiative.getTitle())
                .endDate(initiative.getEndDate())
                .startDate(initiative.getStartDate())
                .status(initiative.getStatus())
                .successCriteria(initiative.getSuccessCriteria())
                .build();
    }
}
