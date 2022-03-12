package com.shanir.launcherservice.model;

import java.util.List;
import java.util.Map;

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

    public String getVersion() {
        return version;
    }

    public Boolean getIsOlympus() {
        return isOlympus;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public Map<String, List<String>> getBaseToProxyAddress() {
        return baseToProxyAddress;
    }
}
