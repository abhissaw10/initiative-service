package com.example.initiativeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "initiative")
@SequenceGenerator(name = "initiative_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
public class Initiative {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initiative_sequence")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "success_criteria", nullable = true)
    private String successCriteria;
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;
    private String status;
    @Column(name="jira_id")
    private Integer jiraId;
    @OneToMany
    @JoinColumn(name = "dependencies")
    private List<Dependency> dependencies;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
