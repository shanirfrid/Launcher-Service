package com.shanir.launcherservice.rest;

import com.shanir.launcherservice.model.DefaultConfiguration;
import com.shanir.launcherservice.model.HostConfiguration;
import com.shanir.launcherservice.model.RetrievedConfiguration;
import com.shanir.launcherservice.service.DefaultConfigurationService;
import com.shanir.launcherservice.service.HostConfigurationService;
import com.shanir.launcherservice.service.LauncherClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public ResponseEntity<Mono<RetrievedConfiguration>> getConfiguration
            (@PathVariable String hostName,
             @PathVariable(required = false) String proxyBase) {
        return new ResponseEntity<>(
                this.hostConfigurationService.getConfiguration(hostName,
                        proxyBase),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteConfiguration/{hostName}")
    public Mono<ResponseEntity<Void>> deleteConfiguration(@PathVariable String hostName) {
        return this.hostConfigurationService.deleteConfiguration(hostName);
    }

    @PostMapping("/updateDefaultConfig")
    public ResponseEntity<Mono<DefaultConfiguration>> updateDefaultConfig(
            @RequestBody DefaultConfiguration defaultConfiguration) {
        return new ResponseEntity<>(this.defaultConfigurationService
                .updateDefaultConfiguration(defaultConfiguration), HttpStatus.OK);
    }

    @GetMapping(value = "/client/latest")
    public ResponseEntity<String> getClientLatest() {
        try {
            return new ResponseEntity<>(
                    this.launcherClientService.getLatestVersion(),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/client/version/{version}")
    public ResponseEntity<Flux<DataBuffer>> getClientVersion(@PathVariable String version) {
        if (!this.launcherClientService.versionExists(version))
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        String launcherPath = this.launcherClientService.getPath(version);
        Path path = Paths.get(launcherPath);

        try {
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(DataBufferUtils.read(
                            resource,
                            new DefaultDataBufferFactory(),
                            4096));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
