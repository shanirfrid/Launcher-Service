package com.shanir.launcherservice.model;

import java.util.Optional;

public class GalaxyHost {
    private String hostName;
    private boolean isCritical;
    private String stationId;
    private String stationName;

    public GalaxyHost(String hostName, boolean isCritical, String stationId,
                      String stationName) {
        this.hostName = hostName;
        this.isCritical = isCritical;
        this.stationId = stationId;
        this.stationName = stationName;
    }

    public GalaxyHost() {
    }

    public Optional<String> getHostName() {
        return Optional.ofNullable(this.hostName);
    }

    public boolean getIsCritical() {
        return this.isCritical;
    }

    public Optional<String> getStationId() {
        return Optional.ofNullable(this.stationId);
    }

    public Optional<String> getStationName() {
        return Optional.ofNullable(this.stationName);
    }
}
