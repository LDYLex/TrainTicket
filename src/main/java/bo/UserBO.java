package bo;

import dao.UserDAO;
import model.User;
import util.SecurityUtils;
import util.ValidationUtils;

public class UserBO 
{

    private UserDAO userDAO = new UserDAO();
    public String msg;

    /**
     * Xử lý nghiệp vụ Đăng nhập
     * @param username
     * @param password
     * @return User nếu đăng nhập thành công, null nếu thất bại
     */
    public User checkLogin(String username, String rawPassword) 
    {
    	// Bước 1: Lấy user từ database dựa vào username (Chỉ lấy username thôi)
        // Lưu ý: Bạn cần sửa lại UserDAO một chút để có hàm tìm User theo Username mà không cần password
        User userFromDB = userDAO.getUserByUsername(username); 

        if (userFromDB == null) 
        {
            return null; // Không tìm thấy user
        }

        // Bước 2: So sánh password nhập vào với password mã hóa trong DB
        boolean isMatched = SecurityUtils.checkPassword(rawPassword, userFromDB.getPassword());

        if (isMatched) 
        {
            return userFromDB; // Đăng nhập thành công
        } 
        else 
        {
            return null; // Sai mật khẩu
        }
    }
    

    /**
     * Xử lý nghiệp vụ Đăng ký tài khoản
     * @param user Đối tượng chứa thông tin đăng ký
     * @return true nếu đăng ký thành công, false nếu có lỗi (trùng tên, dữ liệu sai...)
     */
    public boolean registerUser(User user) 
    {
        // 1. Validate dữ liệu cơ bản
        if (user.getUsername() == null || user.getUsername().length() < 3) {
            msg="Lỗi: Tên đăng nhập quá ngắn.";
            return false;
        }
        
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            msg="Lỗi: Mật khẩu phải từ 6 ký tự trở lên.";
            return false;
        }
     // Check Email
        if (!ValidationUtils.isValidEmail(user.getEmail())) {
            msg="Lỗi: Email không đúng định dạng.";
            return false;
        }

        

        // 2. Kiểm tra nghiệp vụ: Tên đăng nhập đã tồn tại chưa?
        // Mặc dù DAO đã có hàm check, nhưng BO nên kiểm tra để xử lý logic rõ ràng hơn
        if (userDAO.isUsernameExist(user.getUsername())) 
        {
           msg="Lỗi: Tên đăng nhập đã tồn tại.";
            return false;
        }
        

        // 3. Nếu mọi thứ OK, gọi DAO để lưu xuống DB
        // --- MÃ HÓA MẬT KHẨU TRƯỚC KHI LƯU ---
        String hashedPassword = SecurityUtils.hashPassword(user.getPassword());
        user.setPassword(hashedPassword); // Gán lại mật khẩu đã mã hóa

        return userDAO.register(user);
    }
}