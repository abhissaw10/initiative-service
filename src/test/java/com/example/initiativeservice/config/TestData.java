package com.example.initiativeservice.config;

import com.example.initiativeservice.entity.Dependency;
import com.example.initiativeservice.entity.Initiative;
import com.example.initiativeservice.model.InitiativeRequest;
import com.example.initiativeservice.model.InitiativeResponse;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static String TEST_STATUS = "test_status";
    public static LocalDate TEST_START_DATE = LocalDate.of(2023,01,29);
    public static LocalDate TEST_END_DATE = LocalDate.of(2023,12,29);

    public static String TEST_TITLE_1 = "test_title_1";
    public static String TEST_TITLE_2 = "test_title_2";
    public static String TEST_TITLE_3 = "test_title_3";

    public static String TEST_CRITERIA = "test success";

    public static String TEST_INITIATIVE = "test_initiative";

    public static InitiativeRequest initiativeRequest = InitiativeRequest
            .builder()
            .status(TEST_STATUS)
            .title(TEST_TITLE_1)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .build();

    public static InitiativeResponse initiativeResponse2 = InitiativeResponse
            .builder()
            .id(2L)
            .status(TEST_STATUS)
            .title(TEST_TITLE_2)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .build();
    public static InitiativeResponse initiativeResponse = InitiativeResponse
            .builder()
            .id(1L)
            .status(TEST_STATUS)
            .title(TEST_TITLE_1)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .dependencies(List.of(InitiativeResponse.builder().dependencies(List.of(initiativeResponse2)).build()))
            .build();


    public static Initiative initiative = Initiative
            .builder()
            .id(1L)
            .status(TEST_STATUS)
            .title(TEST_TITLE_1)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .build();

    public static Initiative nestedInitiative2 = Initiative
            .builder()
            .id(3L)
            .status(TEST_STATUS)
            .title(TEST_TITLE_3)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .build();

    public static Initiative nestedInitiative = Initiative
            .builder()
            .id(2L)
            .status(TEST_STATUS)
            .title(TEST_TITLE_2)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .dependencies(List.of(Dependency.builder().dependency(1L).build(),Dependency.builder().dependency(3L).build()))
            .build();



}
