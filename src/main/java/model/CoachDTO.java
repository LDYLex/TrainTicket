package model;

import java.util.ArrayList;
import java.util.List;

public class CoachDTO {
    private Long coachId;
    private String coachCode; // VD: Toa 1, Toa A
    private List<SeatDTO> seats = new ArrayList<>(); // Danh sách ghế trong toa này

    public CoachDTO() {}

    // Hàm tiện ích để thêm ghế vào toa
    public void addSeat(SeatDTO seat) {
        this.seats.add(seat);
    }

    // Getters & Setters
    public Long getCoachId() { return coachId; }
    public void setCoachId(Long coachId) { this.coachId = coachId; }
    public String getCoachCode() { return coachCode; }
    public void setCoachCode(String coachCode) { this.coachCode = coachCode; }
    public List<SeatDTO> getSeats() { return seats; }
    public void setSeats(List<SeatDTO> seats) { this.seats = seats; }
}