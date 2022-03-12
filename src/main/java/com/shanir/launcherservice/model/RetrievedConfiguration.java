package com.shanir.launcherservice.model;

import java.util.List;
import java.util.Map;

public class RetrievedConfiguration {
    private String version;
    private Boolean isOlympus;
    private String webAddress;
    private List<String> proxyAddress;

    public RetrievedConfiguration(String version, Boolean isOlympus,
                                  String webAddress, List<String> proxyAddress) {
        this.version = version;
        this.isOlympus = isOlympus;
        this.webAddress = webAddress;
        this.proxyAddress = proxyAddress;
    }

    public RetrievedConfiguration(Configuration configuration,
                                  String proxyBase) {
        this.version = configuration.getVersion();
        this.isOlympus = configuration.getIsOlympus();
        this.webAddress = configuration.getWebAddress();
        Map<String, List<String>> proxyAddressMap =
                configuration.getBaseToProxyAddress();
        this.proxyAddress = proxyAddressMap == null ? null :
                proxyAddressMap.get(proxyBase);
    }

    public String getVersion() {
        return this.version;
    }

    public Boolean getIsOlympus() {
        return this.isOlympus;
    }

    public String getWebAddress() {
        return this.webAddress;
    }

    public List<String> getProxyAddress() {
        return this.proxyAddress;
    }

    public void setEmptyFieldsFromDefault(RetrievedConfiguration defaultConfiguration) {
        this.version =
                this.version == null ? defaultConfiguration.getVersion() :
                        this.version;
        this.isOlympus =
                this.isOlympus == null ? defaultConfiguration.getIsOlympus() :
                        this.isOlympus;
        this.webAddress =
                this.webAddress == null ? defaultConfiguration.getWebAddress() :
                        this.webAddress;
        this.proxyAddress =
                this.proxyAddress == null ? defaultConfiguration.getProxyAddress() :
                        this.proxyAddress;
    }
}
