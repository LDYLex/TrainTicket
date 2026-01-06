package dao;

import model.Connect;
import model.Train;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    // 1. Lấy danh sách tất cả các tàu
    public List<Train> getAllTrains() {
        List<Train> list = new ArrayList<>();
        Connect db = new Connect();
        String sql = "SELECT * FROM trains";

        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Train t = new Train();
                t.setTrainId(rs.getLong("train_id"));
                t.setTrainCode(rs.getString("train_code"));
                t.setTrainName(rs.getString("train_name"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Thêm tàu mới
    public boolean insertTrainWithDetails(Train t, int numCoaches, int seatsPerCoach) 
    {
        Connect db = new Connect();
        Connection cn = null;
        
        try {
            cn = db.getConnection();
            // 1. Tắt chế độ tự động lưu để dùng Transaction
            cn.setAutoCommit(false); 

            // --- BƯỚC A: THÊM TÀU ---
            String sqlTrain = "INSERT INTO trains (train_code, train_name) VALUES (?, ?)";
            // Cần lấy lại ID vừa sinh ra (Statement.RETURN_GENERATED_KEYS)
            PreparedStatement psTrain = cn.prepareStatement(sqlTrain, Statement.RETURN_GENERATED_KEYS);
            psTrain.setString(1, t.getTrainCode());
            psTrain.setString(2, t.getTrainName());
            psTrain.executeUpdate();

            // Lấy train_id vừa tạo
            ResultSet rsTrain = psTrain.getGeneratedKeys();
            Long trainId = null;
            if (rsTrain.next()) {
                trainId = rsTrain.getLong(1);
            } else {
                cn.rollback(); // Lỗi không lấy được ID -> Hủy
                return false;
            }

            // --- BƯỚC B: VÒNG LẶP TẠO TOA ---
            String sqlCoach = "INSERT INTO coaches (train_id, coach_code, seat_count) VALUES (?, ?, ?)";
            PreparedStatement psCoach = cn.prepareStatement(sqlCoach, Statement.RETURN_GENERATED_KEYS);

            String sqlSeat = "INSERT INTO seats (coach_id, seat_number) VALUES (?, ?)";
            PreparedStatement psSeat = cn.prepareStatement(sqlSeat);

            for (int i = 1; i <= numCoaches; i++) {
                // Tạo Toa số i
                psCoach.setLong(1, trainId);
                psCoach.setString(2, "Toa " + i); // Tự đặt tên Toa 1, Toa 2...
                psCoach.setInt(3, seatsPerCoach);
                psCoach.executeUpdate();

                // Lấy coach_id vừa tạo
                ResultSet rsCoach = psCoach.getGeneratedKeys();
                Long coachId = null;
                if (rsCoach.next()) {
                    coachId = rsCoach.getLong(1);
                }

                // --- BƯỚC C: VÒNG LẶP TẠO GHẾ CHO TOA ĐÓ ---
                for (int j = 1; j <= seatsPerCoach; j++) {
                    psSeat.setLong(1, coachId);
                    psSeat.setInt(2, j); // Ghế số 1, 2, 3...
                    psSeat.addBatch();   // Gom lại insert 1 lần cho nhanh
                }
                psSeat.executeBatch(); // Thực thi tạo ghế
            }

            // Mọi thứ Ok hết thì mới LƯU THẬT
            cn.commit();
            return true;

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            try 
            {
                if (cn != null) cn.rollback(); // Có lỗi thì hoàn tác sạch sẽ
            } 
            catch (Exception ex) { ex.printStackTrace(); }
        } 
        finally 
        {
            try 
            {
                if (cn != null) cn.setAutoCommit(true); // Trả lại trạng thái cũ
                if (cn != null) cn.close();
            } 
            catch (Exception ex) { ex.printStackTrace(); }
        }
        return false;
    }

    // 3. Xóa tàu (Cần cẩn thận vì dính khóa ngoại với bảng coaches, train_schedule)
    public int deleteTrainSmart(Long trainId) 
    {
        Connect db = new Connect();
        Connection cn = null;
        
        try {
            cn = db.getConnection();
            cn.setAutoCommit(false); // Bắt đầu Transaction

            // BƯỚC 1: KIỂM TRA LỊCH TRÌNH
            // Nếu tàu dính vào bất kỳ lịch chạy nào -> Chặn luôn
            String sqlCheck = "SELECT COUNT(*) FROM train_schedule WHERE train_id = ?";
            PreparedStatement psCheck = cn.prepareStatement(sqlCheck);
            psCheck.setLong(1, trainId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) 
            {
                cn.rollback();
                return -1; // Mã lỗi: Đang có lịch chạy
            }

            // BƯỚC 2: NẾU KHÔNG CÓ LỊCH -> XÓA SẠCH GHẾ VÀ TOA
            // Xóa ghế trước (Vì ghế nằm trong toa)
            String sqlDelSeats = "DELETE FROM seats WHERE coach_id IN (SELECT coach_id FROM coaches WHERE train_id = ?)";
            PreparedStatement psSeats = cn.prepareStatement(sqlDelSeats);
            psSeats.setLong(1, trainId);
            psSeats.executeUpdate();

            // Xóa toa sau
            String sqlDelCoaches = "DELETE FROM coaches WHERE train_id = ?";
            PreparedStatement psCoaches = cn.prepareStatement(sqlDelCoaches);
            psCoaches.setLong(1, trainId);
            psCoaches.executeUpdate();

            // BƯỚC 3: XÓA TÀU (Cuối cùng)
            String sqlDelTrain = "DELETE FROM trains WHERE train_id = ?";
            PreparedStatement psTrain = cn.prepareStatement(sqlDelTrain);
            psTrain.setLong(1, trainId);
            int result = psTrain.executeUpdate();

            // Nếu chạy đến đây êm xuôi thì Commit
            cn.commit();
            return result > 0 ? 1 : 0;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (cn != null) cn.rollback(); } catch (Exception ex) {}
            return 0; // Lỗi hệ thống
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ex) {}
        }
    }
}