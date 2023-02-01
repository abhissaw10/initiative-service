package com.example.initiativeservice.model;

import com.example.initiativeservice.entity.Initiative;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class InitiativeRequest {
    private String title;
    private String successCriteria;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<Long> dependencies;

    public static Initiative toInitiative(InitiativeRequest request){
        return Initiative
                .builder()
                .startDate(request.startDate)
                .endDate(request.endDate)
                .status(request.status)
                .title(request.title)
                .successCriteria(request.successCriteria)
                .build();
    }
}
