package model;

public class Station {
    private Long stationId;
    private String stationName;
    private String location;

    public Station() {super();}

    public Station(Long stationId, String stationName, String location) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.location = location;
    }

    // Getters and Setters
    public Long getStationId() { return stationId; }
    public void setStationId(Long stationId) { this.stationId = stationId; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}