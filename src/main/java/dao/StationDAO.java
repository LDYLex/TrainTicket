package dao;

import model.Connect;
import model.Station;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    /**
     * Lấy danh sách tất cả các ga để hiển thị lên dropdown
     * @return List<Station>
     */
    public List<Station> getAllStations() {
        List<Station> list = new ArrayList<>();
        Connect db = new Connect();
        
        // Truy vấn lấy tất cả dữ liệu từ bảng stations
        String sql = "SELECT * FROM stations";

        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Station s = new Station();
                // Map dữ liệu từ ResultSet vào Object
                // Tên cột trong chuỗi "" phải khớp chính xác với SQLQuery4.sql
                s.setStationId(rs.getLong("station_id"));
                s.setStationName(rs.getString("station_name"));
                s.setLocation(rs.getString("location"));
                
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy thông tin chi tiết 1 ga theo ID (Dùng khi cần hiển thị tên ga cụ thể)
     * @param id ID của ga
     * @return Đối tượng Station hoặc null nếu không tìm thấy
     */
    public Station getStationById(Long id) {
        Station s = null;
        Connect db = new Connect();
        String sql = "SELECT * FROM stations WHERE station_id = ?";

        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Station();
                    s.setStationId(rs.getLong("station_id"));
                    s.setStationName(rs.getString("station_name"));
                    s.setLocation(rs.getString("location"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}