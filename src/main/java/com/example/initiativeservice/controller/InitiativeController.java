package com.example.initiativeservice.controller;

import com.example.initiativeservice.model.InitiativeRequest;
import com.example.initiativeservice.model.InitiativeResponse;
import com.example.initiativeservice.service.InitiativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class InitiativeController {
    private final InitiativeService initiativeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/initiatives")
    public Long create(@RequestBody InitiativeRequest initiativeRequest){
        return initiativeService.create(initiativeRequest);
    }

    @PutMapping("/v1/initiatives/{initiativeId}")
    public void update( @PathVariable Long initiativeId,@RequestBody InitiativeRequest initiativeRequest){
        initiativeService.update(initiativeRequest,initiativeId);
    }

    @GetMapping("/v1/initiatives")
    public List<InitiativeResponse> getAll(){
        return initiativeService.getAll();
    }

    @GetMapping("/v1/initiatives/{initiativeId}")
    public InitiativeResponse getAll(@PathVariable Long initiativeId){
        return initiativeService.get(initiativeId);
    }

    @GetMapping("/v1/initiatives/byIds")
    public Map<Long,InitiativeResponse> getByIds(@RequestParam("ids") String[] initiativeIds){
        Long[] ids = new Long[initiativeIds.length];
        int index=0;
        for(String initiative: initiativeIds){
            ids[index++]=Long.parseLong(initiative);
        }
        return initiativeService.getByIds(ids);
    }


}
