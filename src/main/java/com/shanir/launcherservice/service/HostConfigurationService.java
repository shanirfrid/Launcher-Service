package com.shanir.launcherservice.service;

import com.shanir.launcherservice.model.RetrievedConfiguration;
import com.shanir.launcherservice.repositories.DefaultConfigurationRepository;
import com.shanir.launcherservice.repositories.HostConfigurationRepository;
import com.shanir.launcherservice.model.GalaxyHost;
import com.shanir.launcherservice.model.HostConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class HostConfigurationService {
    private final HostConfigurationRepository hostConfigurationRepository;
    private final DefaultConfigurationRepository defaultConfigurationRepository;
    private final WebClient webClient = WebClient.create();

    public Mono<HostConfiguration> saveHostConfiguration(
            HostConfiguration hostConfiguration) {
        return this.hostConfigurationRepository.save(hostConfiguration);
    }

    private Mono<Boolean> fetchHostIsCritical(String hostName) {
        final String uri = "http://host.docker.internal:8023/entity/post/"
                + hostName;
        return this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GalaxyHost.class)
                .map(GalaxyHost::getIsCritical);
    }

    private Mono<RetrievedConfiguration> fetchDefaultConfiguration(String hostName,
                                                                   String proxyBase) {
        return fetchHostIsCritical(hostName)
                .flatMap(this.defaultConfigurationRepository::
                        getDefaultConfigurationByCritical)
                .map(defaultConfig -> {
                    if (defaultConfig.getConfiguration().isPresent()){
                        return new RetrievedConfiguration(
                                defaultConfig.getConfiguration().get(),
                                proxyBase);
                    }

                    return new RetrievedConfiguration();
                });

    }

    public Mono<RetrievedConfiguration> fetchAdHocConfiguration(String hostName,
                                                                String proxyBase) {
        return this.hostConfigurationRepository.getConfigurationByHostName(hostName)
                .map(adHocConfig -> {
                    if (adHocConfig.getConfiguration().isPresent())
                        return new RetrievedConfiguration(
                                adHocConfig.getConfiguration().get(),
                                proxyBase);
                    return new RetrievedConfiguration();
                });

    }

    public Mono<RetrievedConfiguration> getConfiguration(String hostName,
                                                         String proxyBase) {
        return fetchDefaultConfiguration(hostName, proxyBase)
                .flatMap(defaultConfig ->
                        fetchAdHocConfiguration(hostName, proxyBase)
                                .defaultIfEmpty(defaultConfig)
                                .map(adhocConfig -> {
                                    adhocConfig.setDefaultConfig(defaultConfig);
                                    return adhocConfig;
                                })
                );
    }

    public Mono<ResponseEntity<Void>> deleteConfiguration(String hostName) {
        return this.hostConfigurationRepository.getConfigurationByHostName(hostName)
                .flatMap(hostConfiguration ->
                        this.hostConfigurationRepository.delete(hostConfiguration)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                        .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
