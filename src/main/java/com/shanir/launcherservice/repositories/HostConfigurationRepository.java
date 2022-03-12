package com.shanir.launcherservice.repositories;

import com.shanir.launcherservice.model.HostConfiguration;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface HostConfigurationRepository extends
        ReactiveMongoRepository<HostConfiguration, String> {

    @Query(value = "{ hostName : { $eq: ?0 }}")
    Mono<HostConfiguration> getConfigurationByHostName(String hostName);
}
