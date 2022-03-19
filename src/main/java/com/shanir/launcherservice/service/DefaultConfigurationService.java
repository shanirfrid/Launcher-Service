package com.shanir.launcherservice.service;

import com.shanir.launcherservice.model.DefaultConfiguration;
import com.shanir.launcherservice.model.HostConfiguration;
import com.shanir.launcherservice.repositories.DefaultConfigurationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DefaultConfigurationService {
    private final DefaultConfigurationRepository defaultConfigurationRepository;

    public Mono<DefaultConfiguration> updateDefaultConfiguration
            (DefaultConfiguration newDefaultConfiguration) {
        return this.defaultConfigurationRepository.getDefaultConfigurationByCritical
                (newDefaultConfiguration.getIsCritical())
                .defaultIfEmpty(newDefaultConfiguration).flatMap(newDefaultConfig -> {
                    newDefaultConfig.updateFields(newDefaultConfiguration);
                    return this.defaultConfigurationRepository.save(newDefaultConfig);
                });

    }
}
