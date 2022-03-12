package com.shanir.launcherservice.rest;

import com.shanir.launcherservice.model.HostConfiguration;
import com.shanir.launcherservice.model.RetrievedConfiguration;
import com.shanir.launcherservice.service.HostConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//
//    @DeleteMapping(value = "/deleteConfiguration/{hostName}")
//    public ResponseEntity<Mono<String>> deleteConfiguration(@PathVariable String hostName) {
//        return new ResponseEntity<>(
//                this.hostConfigurationService.deleteConfiguration(hostName),
//                HttpStatus.OK);
//    }
}
