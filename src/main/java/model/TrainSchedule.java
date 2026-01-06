package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TrainSchedule {
    private Long scheduleId;
    private Long trainId;
    private Long departureStationId;
    private Long arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal priceAdult;
    private BigDecimal priceChild;
    private String status;

    public TrainSchedule() {super();
    }
    
    
    public TrainSchedule(Long scheduleId, Long trainId, Long departureStationId, Long arrivalStationId,
			LocalDateTime departureTime, LocalDateTime arrivalTime, BigDecimal priceAdult, BigDecimal priceChild,
			String status) {
		super();
		this.scheduleId = scheduleId;
		this.trainId = trainId;
		this.departureStationId = departureStationId;
		this.arrivalStationId = arrivalStationId;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.priceAdult = priceAdult;
		this.priceChild = priceChild;
		this.status = status;
	}


	// Getters & Setters ... 
    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public Long getTrainId() { return trainId; }
    public void setTrainId(Long trainId) { this.trainId = trainId; }

    public Long getDepartureStationId() { return departureStationId; }
    public void setDepartureStationId(Long departureStationId) { this.departureStationId = departureStationId; }

    public Long getArrivalStationId() { return arrivalStationId; }
    public void setArrivalStationId(Long arrivalStationId) { this.arrivalStationId = arrivalStationId; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public BigDecimal getPriceAdult() { return priceAdult; }
    public void setPriceAdult(BigDecimal priceAdult) { this.priceAdult = priceAdult; }

    public BigDecimal getPriceChild() { return priceChild; }
    public void setPriceChild(BigDecimal priceChild) { this.priceChild = priceChild; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}