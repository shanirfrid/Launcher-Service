package com.shanir.launcherservice.rest;

import com.shanir.launcherservice.model.Configuration;
import com.shanir.launcherservice.model.HostConfiguration;
import com.shanir.launcherservice.service.HostConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class ConfigurationController {
    private final HostConfigurationService hostConfigurationService;

    @Autowired
    public ConfigurationController(
            HostConfigurationService hostConfigurationService) {
        this.hostConfigurationService = hostConfigurationService;
    }

    @PostMapping("/saveConfiguration")
    public Mono<HostConfiguration> saveConfiguration(
            @RequestBody HostConfiguration hostConfiguration) {
        return this.hostConfigurationService
                .saveHostConfiguration(hostConfiguration);
    }

    @RequestMapping(value = {"/station/config/{hostName}",
            "/station/config/{hostName}/{proxyBase}"})
    public Mono<Configuration> getConfiguration
            (@PathVariable String hostName,
             @PathVariable(required = false) String proxyBase) {
        return this.hostConfigurationService.getConfiguration(hostName, proxyBase);
    }
}

