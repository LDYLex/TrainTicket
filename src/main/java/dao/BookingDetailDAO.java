package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDetailDAO {
	public void insertBookingDetail(Connection cn, Long bookingId, Long scheduleId, Long seatId, BigDecimal price) throws SQLException {
        String sql = "INSERT INTO booking_details (booking_id, schedule_id, travel_date, seat_id, price) " +
                     "VALUES (?, ?, CAST(GETDATE() AS DATE), ?, ?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setLong(1, bookingId);
        ps.setLong(2, scheduleId);
        ps.setLong(3, seatId);
        ps.setBigDecimal(4, price);
        ps.executeUpdate();
    }
}
