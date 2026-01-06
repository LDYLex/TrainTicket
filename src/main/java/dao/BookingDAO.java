package dao;

import model.Connect;
import model.CoachDTO;
import model.SeatDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<CoachDTO> getTrainMap(Long scheduleId, Long trainId) 
    {
        List<CoachDTO> coaches = new ArrayList<>();
        Connect db = new Connect();

        // Câu lệnh lấy Toa và mã hiệu toa
        String sqlCoach = "SELECT coach_id, coach_code FROM coaches WHERE train_id = ? ORDER BY coach_code";
        
        // Câu lệnh lấy Ghế và check trạng thái từ bảng booking_details
        String sqlSeats = "SELECT s.seat_id, s.seat_number, " +
                          "(SELECT COUNT(*) FROM booking_details bd WHERE bd.seat_id = s.seat_id AND bd.schedule_id = ?) as is_booked " +
                          "FROM seats s WHERE s.coach_id = ? ORDER BY s.seat_number";

        try (Connection cn = db.getConnection()) {
            // 1. Lấy danh sách Toa
            PreparedStatement psCoach = cn.prepareStatement(sqlCoach);
            psCoach.setLong(1, trainId);
            ResultSet rsCoach = psCoach.executeQuery();

            while (rsCoach.next()) {
                CoachDTO coach = new CoachDTO();
                coach.setCoachId(rsCoach.getLong("coach_id"));
                coach.setCoachCode(rsCoach.getString("coach_code")); // Trong DB của bạn là coach_code

                // 2. Lấy danh sách Ghế cho toa này
                PreparedStatement psSeat = cn.prepareStatement(sqlSeats);
                psSeat.setLong(1, scheduleId);
                psSeat.setLong(2, coach.getCoachId());
                ResultSet rsSeat = psSeat.executeQuery();

                List<SeatDTO> seatList = new ArrayList<>();
                while (rsSeat.next()) {
                    SeatDTO seat = new SeatDTO();
                    seat.setSeatId(rsSeat.getLong("seat_id"));
                    seat.setSeatNumber(rsSeat.getInt("seat_number"));
                    // seat_type không có trong bảng seats của bạn, có thể mặc định hoặc bỏ qua
                    seat.setBooked(rsSeat.getInt("is_booked") > 0);
                    seatList.add(seat);
                }
                coach.setSeats(seatList);
                coaches.add(coach);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return coaches;
    }
    public Long insertBooking(Connection cn, Long userId, BigDecimal total) throws SQLException 
    {
        String sql = "INSERT INTO bookings (user_id, booking_date, trip_type, total_amount, payment_status) " +
                     "VALUES (?, GETDATE(), 'ONE_WAY', ?, 'PENDING')";
        PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, userId);
        ps.setBigDecimal(2, total);
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        return rs.next() ? rs.getLong(1) : null;
    }
    
}