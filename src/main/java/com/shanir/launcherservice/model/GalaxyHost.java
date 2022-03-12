package com.shanir.launcherservice.model;

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

    public String getHostName() {
        return this.hostName;
    }

    public boolean getIsCritical() {
        return this.isCritical;
    }

    public String getStationId() {
        return this.stationId;
    }

    public String getStationName() {
        return this.stationName;
    }
}
