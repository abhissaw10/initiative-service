package com.example.initiativeservice.service;

import com.example.initiativeservice.model.JiraRequest;
import com.example.initiativeservice.model.JiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "JiraClient", url = "${jira.server.url}", fallback = JiraClientFallback.class)
public interface JiraClient{
    @PostMapping("/rest/structure/2.0/structure")
    JiraResponse createJiraEpic(@RequestBody JiraRequest jiraRequest);
}

class JiraClientFallback implements JiraClient{

    @Override
    public JiraResponse createJiraEpic(JiraRequest jiraRequest) {
        return null;
    }
}
