package dao;

import model.CoachDTO;
import model.Connect;
import model.SeatDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SeatDAO {

    public List<CoachDTO> getCoachesAndSeats(Long scheduleId) {
        // Sử dụng Map để gom nhóm ghế vào đúng Toa (tránh trùng lặp toa)
        // LinkedHashMap giữ thứ tự toa (Toa 1, Toa 2...)
        Map<Long, CoachDTO> coachMap = new LinkedHashMap<>();
        Connect db = new Connect();

        /*
         * Câu lệnh SQL "Thần thánh":
         * 1. Tìm train_id từ schedule_id
         * 2. Lấy danh sách Toa (coaches) và Ghế (seats)
         * 3. LEFT JOIN với booking_details để check ghế đã đặt chưa (dựa vào schedule_id)
         */
        String sql = 
            "SELECT c.coach_id, c.coach_code, " +
            "       s.seat_id, s.seat_number, " +
            "       ts.price_adult, " +
            "       CASE WHEN bd.booking_detail_id IS NOT NULL THEN 1 ELSE 0 END AS is_booked " +
            "FROM train_schedule ts " +
            "JOIN coaches c ON ts.train_id = c.train_id " +
            "JOIN seats s ON c.coach_id = s.coach_id " +
            "LEFT JOIN booking_details bd ON s.seat_id = bd.seat_id AND bd.schedule_id = ts.schedule_id " +
            "WHERE ts.schedule_id = ? " +
            "ORDER BY c.coach_code, s.seat_number";

        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, scheduleId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long coachId = rs.getLong("coach_id");

                    // 1. Nếu Toa chưa có trong Map thì tạo mới
                    CoachDTO coach = coachMap.get(coachId);
                    if (coach == null) {
                        coach = new CoachDTO();
                        coach.setCoachId(coachId);
                        coach.setCoachCode(rs.getString("coach_code"));
                        coachMap.put(coachId, coach);
                    }

                    // 2. Tạo đối tượng Ghế
                    SeatDTO seat = new SeatDTO();
                    seat.setSeatId(rs.getLong("seat_id"));
                    seat.setSeatNumber(rs.getInt("seat_number"));
                    seat.setBooked(rs.getInt("is_booked") == 1); // 1 là đã đặt
                    seat.setPrice(rs.getBigDecimal("price_adult")); // Lấy giá vé cơ bản

                    // 3. Nhét ghế vào Toa
                    coach.addSeat(seat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Chuyển từ Map values sang List để trả về
        return new ArrayList<>(coachMap.values());
    }
}