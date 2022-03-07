package com.shanir.launcherservice.rest;

import com.shanir.launcherservice.model.HostConfiguration;
import com.shanir.launcherservice.service.HostConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConfigurationController {
    private final HostConfigurationService hostConfigurationService;

    @Autowired
    public ConfigurationController(HostConfigurationService hostConfigurationService) {
        this.hostConfigurationService = hostConfigurationService;
    }

    @PostMapping("/addConfiguration")
    public Mono<HostConfiguration> addConfiguration(
            @RequestBody HostConfiguration hostConfiguration){
        return this.hostConfigurationService
                .saveHostConfiguration(hostConfiguration);
    }

}

