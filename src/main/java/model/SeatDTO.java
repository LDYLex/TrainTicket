package model;

import java.math.BigDecimal;

public class SeatDTO {
    private Long seatId;
    private int seatNumber;
    private boolean isBooked; // Quan trọng: True = Đỏ (Đã bán), False = Xanh (Trống)
    private BigDecimal price; // Giá vé của ghế này

    public SeatDTO() {}

    public SeatDTO(Long seatId, int seatNumber, boolean isBooked, BigDecimal price) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
        this.price = price;
    }

    // Getters & Setters
    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { isBooked = booked; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}