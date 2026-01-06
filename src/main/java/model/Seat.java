package model;

public class Seat {
    private Long seatId;
    private Long coachId;
    private int seatNumber;

    public Seat() {super();
    }

    public Seat(Long seatId, Long coachId, int seatNumber) {
        this.seatId = seatId;
        this.coachId = coachId;
        this.seatNumber = seatNumber;
    }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public Long getCoachId() { return coachId; }
    public void setCoachId(Long coachId) { this.coachId = coachId; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
}