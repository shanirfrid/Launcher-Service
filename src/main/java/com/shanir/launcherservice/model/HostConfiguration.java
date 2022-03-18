package com.shanir.launcherservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Data
@Document(collection = "configurations")
public class HostConfiguration {
    @Id
    private String id;
    private String hostName;
    private Configuration configuration;

    public HostConfiguration(String hostName,
                              Configuration configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }
    public HostConfiguration(){

    }

    public Optional<String> getHostName() {
        return Optional.ofNullable(this.hostName);
    }

    public Optional<Configuration> getConfiguration() {
        return Optional.ofNullable(this.configuration);
    }
}
