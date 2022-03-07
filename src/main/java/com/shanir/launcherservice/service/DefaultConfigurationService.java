package com.shanir.launcherservice.service;

import com.shanir.launcherservice.db.DefaultConfigurationRepository;
import com.shanir.launcherservice.model.DefaultConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DefaultConfigurationService {
    private final DefaultConfigurationRepository defaultConfigurationRepository;

    public Mono<DefaultConfiguration> getDefaultConfigurationByCritical(
            boolean isCritical) {
        return this.defaultConfigurationRepository.
                getDefaultConfigurationByCritical(isCritical);
    }
}
