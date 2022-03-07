package com.shanir.launcherservice.db;

import com.shanir.launcherservice.model.DefaultConfiguration;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DefaultConfigurationRepository extends
        ReactiveMongoRepository<DefaultConfiguration, String> {

    @Query(value = "{ isCritical : { $eq: ?0 }}")
    Mono<DefaultConfiguration> getDefaultConfigurationByCritical(
            boolean isCritical);
}
