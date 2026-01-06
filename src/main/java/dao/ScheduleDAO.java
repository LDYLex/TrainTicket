package dao;

import model.Connect;
import model.ScheduleDTO;
import model.TrainSchedule;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    // Hàm lọc linh hoạt: Nếu tham số null thì bỏ qua, nếu có thì lọc
    public List<ScheduleDTO> searchSchedules(Long fromStationId, Long toStationId) 
    {
        List<ScheduleDTO> list = new ArrayList<>();
        Connect db = new Connect();

        StringBuilder sql = new StringBuilder(
            "SELECT ts.schedule_id, t.train_code, " +
            "s1.station_name AS dep_name, " +
            "s2.station_name AS arr_name, " +
            "ts.departure_time, ts.arrival_time, ts.price_adult " +
            "FROM train_schedule ts " +
            "JOIN trains t ON ts.train_id = t.train_id " +
            "JOIN stations s1 ON ts.departure_station = s1.station_id " +
            "JOIN stations s2 ON ts.arrival_station = s2.station_id " +
            "WHERE ts.departure_time >= GETDATE() AND ts.status = 'ACTIVE' "
        );

        // Nối thêm điều kiện nếu có chọn Ga đi
        if (fromStationId != null) {
            sql.append(" AND ts.departure_station = ? ");
        }
        // Nối thêm điều kiện nếu có chọn Ga đến
        if (toStationId != null) {
            sql.append(" AND ts.arrival_station = ? ");
        }

        sql.append(" ORDER BY ts.departure_time ASC");

        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            // Gán giá trị cho dấu ? (Parameter Index khó hơn vì SQL động)
            int index = 1;
            if (fromStationId != null) {
                ps.setLong(index++, fromStationId);
            }
            if (toStationId != null) {
                ps.setLong(index++, toStationId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ScheduleDTO dto = new ScheduleDTO();
                    dto.setScheduleId(rs.getLong("schedule_id"));
                    dto.setTrainCode(rs.getString("train_code"));
                    dto.setDepartureStation(rs.getString("dep_name"));
                    dto.setArrivalStation(rs.getString("arr_name"));
                    dto.setDepartureTime(rs.getTimestamp("departure_time"));
                    dto.setArrivalTime(rs.getTimestamp("arrival_time"));
                    dto.setPriceAdult(rs.getBigDecimal("price_adult"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ScheduleDTO getScheduleById(Long id) 
    {
        ScheduleDTO dto = null;
        Connect db = new Connect();

        String sql = "SELECT ts.schedule_id, t.train_code, t.train_name, " +
                     "s1.station_name AS dep_name, " +
                     "s2.station_name AS arr_name, " +
                     "ts.departure_time, ts.arrival_time, ts.price_adult " +
                     "FROM train_schedule ts " +
                     "JOIN trains t ON ts.train_id = t.train_id " +
                     "JOIN stations s1 ON ts.departure_station = s1.station_id " +
                     "JOIN stations s2 ON ts.arrival_station = s2.station_id " +
                     "WHERE ts.schedule_id = ?";

        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new ScheduleDTO();
                    dto.setScheduleId(rs.getLong("schedule_id"));
                    dto.setTrainCode(rs.getString("train_code")); 
                    // Nếu ScheduleDTO của bạn có thuộc tính trainName thì set thêm:
                    // dto.setTrainName(rs.getString("train_name"));
                    
                    dto.setDepartureStation(rs.getString("dep_name"));
                    dto.setArrivalStation(rs.getString("arr_name"));
                    dto.setDepartureTime(rs.getTimestamp("departure_time"));
                    dto.setArrivalTime(rs.getTimestamp("arrival_time"));
                    dto.setPriceAdult(rs.getBigDecimal("price_adult"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
 // 1. ADMIN: Lấy tất cả lịch trình 
    public List<ScheduleDTO> getAllSchedules() 
    {
        List<ScheduleDTO> list = new ArrayList<>();
        Connect db = new Connect();
        String sql = "SELECT ts.schedule_id, t.train_code, " +
                     "s1.station_name AS dep_name, " +
                     "s2.station_name AS arr_name, " +
                     "ts.departure_time, ts.arrival_time, ts.price_adult " +
                     "FROM train_schedule ts " +
                     "JOIN trains t ON ts.train_id = t.train_id " +
                     "JOIN stations s1 ON ts.departure_station = s1.station_id " +
                     "JOIN stations s2 ON ts.arrival_station = s2.station_id " +
                     " WHERE ts.departure_time >= CAST(GETDATE() AS DATE) "+
                     "ORDER BY ts.departure_time DESC"; // Mới nhất lên đầu
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) 
        {
            while (rs.next()) {
                ScheduleDTO dto = new ScheduleDTO();
                dto.setScheduleId(rs.getLong("schedule_id"));
                dto.setTrainCode(rs.getString("train_code"));
                dto.setDepartureStation(rs.getString("dep_name"));
                dto.setArrivalStation(rs.getString("arr_name"));
                dto.setDepartureTime(rs.getTimestamp("departure_time"));
                dto.setArrivalTime(rs.getTimestamp("arrival_time"));
                dto.setPriceAdult(rs.getBigDecimal("price_adult"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. ADMIN: Thêm lịch trình mới
    public boolean insertSchedule(TrainSchedule ts) {
        Connect db = new Connect();
        
        // SỬA CÂU SQL: Thêm cột price_child vào danh sách insert
        String sql = "INSERT INTO train_schedule " +
                     "(train_id, departure_station, arrival_station, " +
                     "departure_time, arrival_time, price_adult, price_child, status) " + // Thêm price_child
                     "VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVE')"; // Thêm 1 dấu ? nữa
        
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setLong(1, ts.getTrainId());
            ps.setLong(2, ts.getDepartureStationId());
            ps.setLong(3, ts.getArrivalStationId());
            ps.setTimestamp(4, Timestamp.valueOf(ts.getDepartureTime()));
            ps.setTimestamp(5, Timestamp.valueOf(ts.getArrivalTime()));
            
            // --- Set giá vé người lớn ---
            ps.setBigDecimal(6, ts.getPriceAdult());

            // --- XỬ LÝ GIÁ VÉ TRẺ EM (MỚI) ---
            // Logic: Giá trẻ em = 50% giá người lớn (0.5)
            // Hoặc bạn có thể để 75% (0.75) tùy quy định
            BigDecimal priceAdult = ts.getPriceAdult();
            BigDecimal priceChild = priceAdult.multiply(new java.math.BigDecimal("0.5")); 
            
            ps.setBigDecimal(7, priceChild); // Set giá trị vào dấu ? thứ 7

            return ps.executeUpdate() > 0;
            
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }

    // 3. ADMIN: Xóa lịch trình
    public boolean deleteSchedule(Long id) {
        Connect db = new Connect();
        String sql = "DELETE FROM train_schedule WHERE schedule_id = ?";
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
 // 4. KIỂM TRA TRÙNG LỊCH (Time Overlap)
    // Logic: Một chuyến tàu bị coi là trùng nếu:
    // (Start mới < End cũ) VÀ (End mới > Start cũ)
    public boolean isTrainBusy(Long trainId, Timestamp newStart, Timestamp newEnd) {
        Connect db = new Connect();
        // Câu lệnh đếm số chuyến tàu vi phạm điều kiện thời gian
        String sql = "SELECT COUNT(*) FROM train_schedule " +
                     "WHERE train_id = ? " +
                     "AND departure_time < ? AND arrival_time > ? " +
                     "AND status = 'ACTIVE'"; 
        
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setLong(1, trainId);
            ps.setTimestamp(2, newEnd);   // End mới
            ps.setTimestamp(3, newStart); // Start mới
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu > 0 tức là có trùng
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return true; // Mặc định trả về true (busy) để chặn nếu có lỗi
    }

    // 5. KIỂM TRA TÍNH HỢP LÝ VỊ TRÍ (Location Continuity)
    // Kiểm tra xem tàu đang ở đâu dựa trên chuyến đi kết thúc gần nhất trước đó
    public String checkLocationConflict(Long trainId, Long newDepStationId, Timestamp newStart) {
        Connect db = new Connect();
        
        // Lấy chuyến tàu gần nhất mà kết thúc TRƯỚC thời gian khởi hành mới
        String sql = "SELECT TOP 1 s.station_id, s.station_name, ts.arrival_time " +
                     "FROM train_schedule ts " +
                     "JOIN stations s ON ts.arrival_station = s.station_id " +
                     "WHERE ts.train_id = ? AND ts.arrival_time <= ? " +
                     "ORDER BY ts.arrival_time DESC";
        
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setLong(1, trainId);
            ps.setTimestamp(2, newStart);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long lastArrivalStationId = rs.getLong("station_id");
                    String lastStationName = rs.getString("station_name");
                    Timestamp lastArrivalTime = rs.getTimestamp("arrival_time");
                    
                    // Logic: Nếu Ga đến của chuyến trước KHÁC Ga đi của chuyến mới
                    if (!lastArrivalStationId.equals(newDepStationId)) {
                        long diffHours = (newStart.getTime() - lastArrivalTime.getTime()) / (60 * 60 * 1000);
                        
                        // Nếu khoảng cách thời gian quá ngắn (ví dụ dưới 12 tiếng) mà lại đổi ga -> BÁO LỖI
                        // (Giả sử tàu cần thời gian dài để di chuyển không tải giữa các ga xa)
                        if (diffHours < 8) {
                            return "Tàu vừa kết thúc hành trình tại " + lastStationName + 
                                   " vào lúc " + lastArrivalTime + ". Không thể xuất phát ngay tại ga khác!";
                        }
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null; // Null nghĩa là OK, không có lỗi
    }
}