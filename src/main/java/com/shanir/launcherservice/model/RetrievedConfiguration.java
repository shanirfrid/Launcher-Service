package com.shanir.launcherservice.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RetrievedConfiguration {
    private String version;
    private Boolean isOlympus;
    private String webAddress;
    private List<String> proxyAddress;
    private RetrievedConfiguration defaultConfiguration;

    public RetrievedConfiguration(String version, Boolean isOlympus,
                                  String webAddress, List<String> proxyAddress) {
        this.version = version;
        this.isOlympus = isOlympus;
        this.webAddress = webAddress;
        this.proxyAddress = proxyAddress;
    }

    public RetrievedConfiguration(){

    }

    public RetrievedConfiguration(Configuration configuration,
                                  String proxyBase) {
        this.version = configuration.getVersion().isPresent()?
                configuration.getVersion().get() : null;

        this.isOlympus = configuration.getIsOlympus().isPresent()?
                configuration.getIsOlympus().get() : null;

        this.webAddress = configuration.getWebAddress().isPresent()?
                configuration.getWebAddress().get() : null;

        this.version = configuration.getVersion().isPresent()?
                configuration.getVersion().get() : null;


        Map<String, List<String>> proxyAddressMap =
                configuration.getBaseToProxyAddress().isPresent() ?
                        configuration.getBaseToProxyAddress().get() : null;

        this.proxyAddress = proxyAddressMap == null ? null :
                proxyAddressMap.get(proxyBase);
    }

    public Optional<String> getVersion() {
        if (this.version == null && this.defaultConfiguration != null)
            return this.defaultConfiguration.getVersion();

        return Optional.ofNullable(this.version);
    }

    public Optional<Boolean> getIsOlympus() {
        if (this.isOlympus == null && this.defaultConfiguration != null)
            return defaultConfiguration.getIsOlympus();

        return Optional.ofNullable(this.isOlympus);
    }

    public Optional<String> getWebAddress() {
        if (this.webAddress == null && this.defaultConfiguration != null)
            return defaultConfiguration.getWebAddress();

        return Optional.ofNullable(this.webAddress);
    }

    public Optional<List<String>> getProxyAddress() {
        if (this.proxyAddress == null && this.defaultConfiguration != null)
            return defaultConfiguration.getProxyAddress();

        return Optional.ofNullable(this.proxyAddress);
    }

    public void setEmptyFieldsFromDefault(RetrievedConfiguration defaultConfiguration) {
        this.defaultConfiguration = defaultConfiguration;
    }
}
