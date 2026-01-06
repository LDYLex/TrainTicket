package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Connect;
import model.User;

public class UserDAO {

    /**
     * Phương thức kiểm tra username đã tồn tại chưa (Dùng khi validate form đăng ký)
     */
	public boolean isUsernameExist(String username) 
	{
        Connect db = new Connect();
        String sql = "SELECT user_id FROM users WHERE username = ?";
        
        try (Connection cn = db.getConnection(); 
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có dòng kết quả trả về nghĩa là đã tồn tại
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức kiểm tra đăng nhập
     * @param username //ghi chu javadoc
     * @param password
     * @return Trả về đối tượng User nếu đúng, null nếu sai
     */
	public User checkLogin(String username, String password) 
	{
	    User user = null;
	    Connect db = new Connect(); // Tạo đối tượng Connect
	    
	    // Sử dụng try-with-resources (tự động đóng kết nối khi xong - Rất quan trọng)
	    try (Connection cn = db.getConnection(); //viet cach nay cả 'ps' và 'cn' sẽ tự động được đóng (close) mà bạn không cần viết lệnh .close()
	         PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) 
	    	{
	        
		        ps.setString(1, username);
		        ps.setString(2, password);
		        
		        try (ResultSet rs = ps.executeQuery()) 
		        {
		        	if (rs.next()) 
		        	{
		                user = new User();
		                user.setUserId(rs.getLong("user_id"));
		                user.setUsername(rs.getString("username"));
		                user.setPassword(rs.getString("password")); // Thường không nên set password trả về để bảo mật
		                user.setEmail(rs.getString("email"));
		                user.setFullName(rs.getString("full_name"));
		                user.setRole(rs.getString("role"));
		                
		                // Chuyển đổi Timestamp của SQL sang LocalDateTime của Java
		                if (rs.getTimestamp("created_at") != null) 
		                {
		                    user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		                }
		                
		        	}
	        
		        }
	    		}
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    // Không cần finally để close cn nữa vì try(...) đã tự làm rồi
	    
	    return user;
	    /*
	     Tự động đóng kết nối: Ngay cả khi code bên trong xảy ra lỗi (Exception), Java vẫn đảm bảo lệnh cn.close() và ps.close() được thực thi.

			Code ngắn gọn: Bạn không cần phải viết khối finally dài dòng để kiểm tra if (cn != null) cn.close().
			
			Tránh rò rỉ bộ nhớ (Memory Leak): Đảm bảo số lượng kết nối đến SQL Server không bị treo do quên đóng.*/
	}

    /**
     * Phương thức đăng ký tài khoản mới
     * @param newUser Đối tượng user chứa thông tin đăng ký
     * @return true nếu đăng ký thành công, false nếu thất bại (trùng tên, lỗi mạng...)
     */
	public boolean register(User newUser) 
	{
        // 1. Kiểm tra username đã tồn tại chưa trước khi insert
        if (isUsernameExist(newUser.getUsername())) 
        {
            return false; 
        }

        Connect db = new Connect();
        // SQL: user_id tự tăng, created_at dùng GETDATE() của SQL Server
        String sql = "INSERT INTO users (username, password, email, full_name, role, created_at) "
                   + "VALUES (?, ?, ?, ?, 'USER', GETDATE())";
        
        try (Connection cn = db.getConnection(); 
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, newUser.getUsername());
            ps.setString(2, newUser.getPassword());
            ps.setString(3, newUser.getEmail());
            ps.setString(4, newUser.getFullName());

            int rows = ps.executeUpdate();
            return rows > 0; // Trả về true nếu thêm được 1 dòng trở lên
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	/**
     * Tìm thông tin user dựa vào username (Dùng cho chức năng Login với BCrypt)
     * @param username tên đăng nhập cần tìm
     * @return User object chứa mật khẩu đã mã hóa, hoặc null nếu không tìm thấy
     */
    public User getUserByUsername(String username) {
        User user = null;
        Connect db = new Connect();
        
        // Câu lệnh SQL chỉ tìm theo username, KHÔNG check password ở đây
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection cn = db.getConnection(); 
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getLong("user_id"));
                    user.setUsername(rs.getString("username"));
                    
                    // QUAN TRỌNG: Đây là chuỗi Hash (ví dụ: $2a$10$D8...), không phải pass thường
                    user.setPassword(rs.getString("password")); 
                    
                    user.setEmail(rs.getString("email"));
                    user.setFullName(rs.getString("full_name"));
                    user.setRole(rs.getString("role"));
                    
                    // Xử lý ngày tạo (nếu có)
                    if (rs.getTimestamp("created_at") != null) {
                         user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
   
}