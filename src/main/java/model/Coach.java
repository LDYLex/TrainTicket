package model;

public class Coach {
    private Long coachId;
    private Long trainId;
    private String coachCode; // Ví dụ: Toa 1, Toa A
    private int seatCount;

    public Coach() {super();
    }

    public Coach(Long coachId, Long trainId, String coachCode, int seatCount) {
        this.coachId = coachId;
        this.trainId = trainId;
        this.coachCode = coachCode;
        this.seatCount = seatCount;
    }

    public Long getCoachId() { return coachId; }
    public void setCoachId(Long coachId) { this.coachId = coachId; }

    public Long getTrainId() { return trainId; }
    public void setTrainId(Long trainId) { this.trainId = trainId; }

    public String getCoachCode() { return coachCode; }
    public void setCoachCode(String coachCode) { this.coachCode = coachCode; }

    public int getSeatCount() { return seatCount; }
    public void setSeatCount(int seatCount) { this.seatCount = seatCount; }
}