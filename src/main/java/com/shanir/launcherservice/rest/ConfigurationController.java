package com.shanir.launcherservice.rest;

import com.shanir.launcherservice.model.DefaultConfiguration;
import com.shanir.launcherservice.model.HostConfiguration;
import com.shanir.launcherservice.model.RetrievedConfiguration;
import com.shanir.launcherservice.service.DefaultConfigurationService;
import com.shanir.launcherservice.service.HostConfigurationService;
import com.shanir.launcherservice.service.LauncherClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class ConfigurationController {
    private final HostConfigurationService hostConfigurationService;
    private final DefaultConfigurationService defaultConfigurationService;
    private final LauncherClientService launcherClientService;

    @Autowired
    public ConfigurationController(
            HostConfigurationService hostConfigurationService,
            DefaultConfigurationService defaultConfigurationService,
            LauncherClientService launcherClientService) {
        this.hostConfigurationService = hostConfigurationService;
        this.defaultConfigurationService = defaultConfigurationService;
        this.launcherClientService = launcherClientService;
    }

    @PostMapping("/saveConfiguration")
    public ResponseEntity<Mono<HostConfiguration>> saveConfiguration(
            @RequestBody HostConfiguration hostConfiguration) {
        return new ResponseEntity<>(this.hostConfigurationService
                .saveHostConfiguration(hostConfiguration), HttpStatus.OK);
    }

    @RequestMapping(value = {"/station/config/{hostName}",
            "/station/config/{hostName}/{proxyBase}"})
    public Mono<RetrievedConfiguration> getConfiguration
            (@PathVariable String hostName,
             @PathVariable(required = false) String proxyBase) {
        return this.hostConfigurationService.getConfiguration(hostName, proxyBase);
    }

    @DeleteMapping(value = "/deleteConfiguration/{hostName}")
    public ResponseEntity<Mono<String>> deleteConfiguration(@PathVariable String hostName) {
        return new ResponseEntity<>(
                this.hostConfigurationService.deleteConfiguration(hostName),
                HttpStatus.OK);
    }

    @PostMapping("/updateDefaultConfig")
    public ResponseEntity<Mono<DefaultConfiguration>> updateDefaultConfig(
            @RequestBody DefaultConfiguration defaultConfiguration) {
        return new ResponseEntity<>(this.defaultConfigurationService
                .updateDefaultConfiguration(defaultConfiguration), HttpStatus.OK);
    }

    @GetMapping(value = "/client/latest")
    public String getClientLatest() {
        return this.launcherClientService.getLatestVersion();
    }
}
