package com.shanir.launcherservice.model;

public class GalaxyHost {
    String hostName;
    boolean isCritical;
    String stationId;
    String stationName;

    public GalaxyHost(String hostName, boolean isCritical, String stationId,
                      String stationName) {
        this.hostName = hostName;
        this.isCritical = isCritical;
        this.stationId = stationId;
        this.stationName = stationName;
    }

    public GalaxyHost() {
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setIsCritical(boolean isCritical) {
        this.isCritical = isCritical;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getHostName() {
        return hostName;
    }

    public boolean getIsCritical() {
        return isCritical;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }
}
