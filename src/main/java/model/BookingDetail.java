package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingDetail {
    private Long bookingDetailId;
    private Long bookingId;
    private Long scheduleId;
    private LocalDate travelDate;
    private Long seatId;        // Thay thế cho adults/children
    private BigDecimal price;   // Dùng BigDecimal cho tiền tệ

    public BookingDetail() {super();
    }

    public BookingDetail(Long bookingDetailId, Long bookingId, Long scheduleId, LocalDate travelDate, Long seatId, BigDecimal price) {
        this.bookingDetailId = bookingDetailId;
        this.bookingId = bookingId;
        this.scheduleId = scheduleId;
        this.travelDate = travelDate;
        this.seatId = seatId;
        this.price = price;
    }

    public Long getBookingDetailId() { return bookingDetailId; }
    public void setBookingDetailId(Long bookingDetailId) { this.bookingDetailId = bookingDetailId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}