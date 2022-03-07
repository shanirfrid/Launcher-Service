package com.shanir.launcherservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "configurations")
public class HostConfiguration {
    @Id
    private String id;
    private String hostName;
    private Configuration configuration;

    public HostConfiguration(String hostName,Configuration configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }
}
