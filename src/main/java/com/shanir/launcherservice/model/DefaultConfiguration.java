package com.shanir.launcherservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Data
@Document(collection = "defaultConfiguration")
public class DefaultConfiguration {
    @Id
    private String id;
    private boolean isCritical;
    private Configuration configuration;

    public DefaultConfiguration(boolean isCritical, Configuration configuration) {
        this.isCritical = isCritical;
        this.configuration = configuration;
    }
    public DefaultConfiguration(){

    }

    public boolean getIsCritical() {
        return this.isCritical;
    }

    public Optional<Configuration> getConfiguration() {
        return Optional.ofNullable(this.configuration);
    }

    public void updateFields(DefaultConfiguration newDefaultConfiguration) {
        if (newDefaultConfiguration.getConfiguration().isPresent())
            this.configuration.updateConfiguration
                    (newDefaultConfiguration.getConfiguration().get());
    }
}
