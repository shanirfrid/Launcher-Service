package com.shanir.launcherservice.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Configuration {
    private String version;
    private Boolean isOlympus;
    private String webAddress;
    private Map<String, List<String>> baseToProxyAddress;

    public Configuration(String version, Boolean isOlympus,
                         String webAddress, Map<String,
            List<String>> baseToProxyAddress) {
        this.version = version;
        this.isOlympus = isOlympus;
        this.webAddress = webAddress;
        this.baseToProxyAddress = baseToProxyAddress;
    }
    public Configuration(){

    }

    public Optional<String> getVersion() {
        return Optional.ofNullable(this.version);
    }

    public Optional<Boolean> getIsOlympus() {
        return Optional.ofNullable(this.isOlympus);
    }

    public Optional<String> getWebAddress() {
        return Optional.ofNullable(this.webAddress);
    }

    public Optional<Map<String, List<String>>> getBaseToProxyAddress() {
        return Optional.ofNullable(this.baseToProxyAddress);
    }

    public void updateConfiguration(Configuration configuration) {
        this.version = configuration.getVersion().isPresent() ?
                configuration.getVersion().get() : this.version;
        this.isOlympus = configuration.getIsOlympus().isPresent() ?
                configuration.getIsOlympus().get() : this.isOlympus;
        this.webAddress = configuration.getWebAddress().isPresent() ?
                configuration.getWebAddress().get() : this.webAddress;
        this.baseToProxyAddress = configuration.getBaseToProxyAddress().isPresent() ?
                configuration.getBaseToProxyAddress().get() : this.baseToProxyAddress;
    }
}
