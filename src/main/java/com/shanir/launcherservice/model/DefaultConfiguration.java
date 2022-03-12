package com.shanir.launcherservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "defaultConfiguration")
public class DefaultConfiguration {
    @Id
    private String Id;
    private boolean isCritical;
    private Configuration configuration;

    public DefaultConfiguration(boolean isCritical, Configuration configuration) {
        this.isCritical = isCritical;
        this.configuration = configuration;
    }

    public boolean getIsCritical() {
        return isCritical;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
