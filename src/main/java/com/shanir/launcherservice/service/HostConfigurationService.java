package com.shanir.launcherservice.service;

import com.shanir.launcherservice.db.HostConfigurationRepository;
import com.shanir.launcherservice.model.HostConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class HostConfigurationService {
    private final HostConfigurationRepository hostConfigurationRepository;

    public Mono<HostConfiguration> saveHostConfiguration(
            HostConfiguration hostConfiguration) {
        return this.hostConfigurationRepository.save(hostConfiguration);
    }

    public Mono<HostConfiguration> getConfigurationByHostName(String hostName) {
        return this.hostConfigurationRepository.
                getConfigurationByHostName(hostName);
    }
}
