package com.example.initiativeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dependency")
@Entity
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long dependency;
}
