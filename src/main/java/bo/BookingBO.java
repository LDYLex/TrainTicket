package bo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dao.BookingDAO;
import dao.BookingDetailDAO;
import model.Connect;

public class BookingBO {
    private BookingDAO bookingDAO = new BookingDAO();
    private BookingDetailDAO bookingDetail= new BookingDetailDAO();

    public boolean createBookingTransaction(Long userId, Long scheduleId, List<Long> seatIds, BigDecimal price) {
        Connection cn = null;
        try {
            cn = new Connect().getConnection();
            cn.setAutoCommit(false); // Bắt đầu Transaction ở tầng nghiệp vụ

            // Bước 1: Gọi DAO lưu Booking
            BigDecimal total = price.multiply(new BigDecimal(seatIds.size()));
            Long bookingId = bookingDAO.insertBooking(cn, userId, total);

            // Bước 2: Duyệt danh sách ghế và gọi DAO lưu chi tiết
            if (bookingId != null) {
                for (Long seatId : seatIds) {
                    bookingDetail.insertBookingDetail(cn, bookingId, scheduleId, seatId, price);
                }
            }

            cn.commit(); // Tất cả thành công thì mới chốt dữ liệu
            return true;
        } 
        catch (Exception e) 
        {
            if (cn != null) try { cn.rollback(); } catch (SQLException ex) {} // Lỗi thì trả lại trạng thái cũ
            e.printStackTrace();
            return false;
        } 
        finally 
        {
            if (cn != null) try { cn.close(); } catch (SQLException e) {}
        }
    }
}
