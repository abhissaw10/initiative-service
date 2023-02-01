package com.example.initiativeservice.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JiraResponse {

    private Integer id;
    private String name;
    private String description;
    private String owner;
}
