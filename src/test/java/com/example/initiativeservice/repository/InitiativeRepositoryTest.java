package com.example.initiativeservice.repository;

import com.example.initiativeservice.entity.Dependency;
import com.example.initiativeservice.entity.Initiative;
import com.example.initiativeservice.model.InitiativeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.example.initiativeservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles(value = "integration-test")
public class InitiativeRepositoryTest {

    @Autowired InitiativeRepository initiativeRepository;

    @Test
    public void save(){
        Initiative dependencyInitiative = Initiative
                .builder()
                .title(TEST_TITLE_2)
                .status(InitiativeStatus.DRAFT.name())
                .successCriteria(TEST_CRITERIA)
                .startDate(TEST_START_DATE)
                .endDate(TEST_END_DATE)
                .status(TEST_STATUS).build();

        Initiative response1 = initiativeRepository.save(dependencyInitiative);
        Dependency dependency = new Dependency();
        dependency.setDependency(response1.getId());

        Initiative response2 = initiativeRepository.save(Initiative
                .builder()
                .title(TEST_TITLE_1)
                .status(InitiativeStatus.DRAFT.name())
                .successCriteria(TEST_CRITERIA)
                .startDate(TEST_START_DATE)
                .endDate(TEST_END_DATE)
                .status(TEST_STATUS)
                        .dependencies(List.of(dependency))
                .build());

        Optional<Initiative> initiativeOptional = initiativeRepository.findById(response2.getId());
        assertThat(initiativeOptional.get().getDependencies().size()).isEqualTo(1);
        assertThat(initiativeOptional.get().getDependencies().get(0).getDependency()).isEqualTo(response1.getId());
    }
}
