package com.shanir.launcherservice.service;

import com.shanir.launcherservice.model.Configuration;
import com.shanir.launcherservice.repositories.DefaultConfigurationRepository;
import com.shanir.launcherservice.repositories.HostConfigurationRepository;
import com.shanir.launcherservice.model.GalaxyHost;
import com.shanir.launcherservice.model.HostConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class HostConfigurationService {
    private final HostConfigurationRepository hostConfigurationRepository;
    private final DefaultConfigurationRepository defaultConfigurationRepository;

    public Mono<HostConfiguration> saveHostConfiguration(
            HostConfiguration hostConfiguration) {
        return this.hostConfigurationRepository.save(hostConfiguration);
    }

    private Mono<Boolean> fetchHostIsCritical(String hostName) {
        final String uri = "http://localhost:8023/entity/post/"
                + hostName;
        return WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GalaxyHost.class)
                .map(GalaxyHost::getIsCritical);
    }

    private Mono<Configuration> getDefaultConfiguration(String hostName,
                                                        String proxyBase) {
        return fetchHostIsCritical(hostName)
                .flatMap(this.defaultConfigurationRepository::getDefaultConfigurationByCritical)
                .map(defaultConfig -> defaultConfig.getConfiguration(proxyBase));
    }

    public Mono<Configuration> getConfiguration(String hostName,
                                                String proxyBase) {
        return this.hostConfigurationRepository
                .getConfigurationByHostName(hostName)
                .flatMap(hostConfiguration -> hostConfiguration.getIsDefault() ?
                        this.getDefaultConfiguration(hostName, proxyBase) :
                        Mono.just(hostConfiguration.getConfiguration()));
    }
}
