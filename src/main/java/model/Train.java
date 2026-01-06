package model;

public class Train {
    private Long trainId;
    private String trainCode;
    private String trainName;

    public Train() {super();
    }

    public Train(Long trainId, String trainCode, String trainName) {
        this.trainId = trainId;
        this.trainCode = trainCode;
        this.trainName = trainName;
    }

    public Long getTrainId() { return trainId; }
    public void setTrainId(Long trainId) { this.trainId = trainId; }

    public String getTrainCode() { return trainCode; }
    public void setTrainCode(String trainCode) { this.trainCode = trainCode; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
}