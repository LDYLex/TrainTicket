package model;

import java.math.BigDecimal;
import java.sql.Timestamp; // Dùng Timestamp để JSTL dễ format ngày giờ

public class ScheduleDTO {
    private Long scheduleId;
    private String trainCode;       // Mã tàu (VD: SE1)
    private String departureStation; // Tên ga đi
    private String arrivalStation;   // Tên ga đến
    private Timestamp departureTime; // Giờ đi
    private Timestamp arrivalTime;   // Giờ đến
    private BigDecimal priceAdult;   // Giá vé tham khảo

    public ScheduleDTO() {}

    // Getters and Setters
    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
    
    public String getTrainCode() { return trainCode; }
    public void setTrainCode(String trainCode) { this.trainCode = trainCode; }
    
    public String getDepartureStation() { return departureStation; }
    public void setDepartureStation(String departureStation) { this.departureStation = departureStation; }
    
    public String getArrivalStation() { return arrivalStation; }
    public void setArrivalStation(String arrivalStation) { this.arrivalStation = arrivalStation; }
    
    public Timestamp getDepartureTime() { return departureTime; }
    public void setDepartureTime(Timestamp departureTime) { this.departureTime = departureTime; }
    
    public Timestamp getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(Timestamp arrivalTime) { this.arrivalTime = arrivalTime; }
    
    public BigDecimal getPriceAdult() { return priceAdult; }
    public void setPriceAdult(BigDecimal priceAdult) { this.priceAdult = priceAdult; }
}