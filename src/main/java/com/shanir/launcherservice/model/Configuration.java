package com.shanir.launcherservice.model;

import java.util.List;

public class Configuration {
    private String version;
    private boolean isOlympus;
    private String webAddress;
    private  List<String> proxyAddress;

    public Configuration(String version, boolean isOlympus, String webAddress,
                         List<String> proxyAddress) {
        this.version = version;
        this.isOlympus = isOlympus;
        this.webAddress = webAddress;
        this.proxyAddress = proxyAddress;
    }

    public String getVersion() {
        return version;
    }

    public boolean getIsOlympus() {
        return isOlympus;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public List<String> getProxyAddress() {
        return proxyAddress;
    }
}
