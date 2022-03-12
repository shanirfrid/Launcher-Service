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
    private boolean isDefault;
    private Configuration configuration;

    public HostConfiguration(String hostName,
                             boolean isDefault, Configuration configuration) {
        this.hostName = hostName;
        this.isDefault = isDefault;
        this.configuration = configuration;
    }

    public String getHostName() {
        return this.hostName;
    }

    public boolean getIsDefault() {
        return this.isDefault;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }
}
