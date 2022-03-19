package com.shanir.launcherservice.service;

import com.shanir.launcherservice.model.RetrievedConfiguration;
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
    private final WebClient webClient = WebClient.create();

    public Mono<HostConfiguration> saveHostConfiguration(
            HostConfiguration hostConfiguration) {
        return this.hostConfigurationRepository.save(hostConfiguration);
    }

    private Mono<Boolean> fetchHostIsCritical(String hostName) {
        final String uri = "http://localhost:8023/entity/post/"
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

                                    if (adhocConfig != defaultConfig)
                                        adhocConfig.setEmptyFieldsFromDefault(defaultConfig);

                                    return adhocConfig;
                                })
                );
    }

    public Mono<String> deleteConfiguration(String hostName) {
        String defaultDeleteMessage = hostName + " configuration is default " +
                "and therefore cannot by deleted only edited";
        String nonDefaultDeleteMessage = hostName + " configuration deleted";

        return this.hostConfigurationRepository.getConfigurationByHostName(hostName)
                .flatMap(hostConfiguration ->
                        this.hostConfigurationRepository.delete(hostConfiguration)
                                .thenReturn(nonDefaultDeleteMessage))
                .defaultIfEmpty(defaultDeleteMessage);
    }
}
