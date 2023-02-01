package com.example.initiativeservice;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static com.example.initiativeservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration-test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "hibernate.temp.use_jdbc_metadata_defaults=true")
public class InitiativeIntegrationTest {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("initiative");

    @Autowired
    TestRestTemplate testRestTemplate;

    @DynamicPropertySource
    public static void setup(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",()->container.getJdbcUrl());
        registry.add("spring.datasource.username",()->container.getUsername());
        registry.add("spring.datasource.password",()->container.getPassword());
    }

    @Test
    void shouldCreateInitiative(){
        HttpEntity entity = new HttpEntity(initiativeRequest,new HttpHeaders());
        ResponseEntity<Long> exchange = testRestTemplate
                .exchange("/v1/initiatives",
                        HttpMethod.POST, entity, Long.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
