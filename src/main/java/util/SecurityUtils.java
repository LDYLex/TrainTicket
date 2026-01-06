package util;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtils {

    /**
     * Mã hóa mật khẩu
     * @param plainPassword mật khẩu gốc (người dùng nhập)
     * @return chuỗi hash đã mã hóa (lưu vào Database)
     */
    public static String hashPassword(String plainPassword) {
        // Gensalt mặc định là 10 (độ phức tạp)
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Kiểm tra mật khẩu khi đăng nhập
     * @param plainPassword mật khẩu người dùng nhập vào
     * @param hashedPassword mật khẩu đã mã hóa lấy từ Database
     * @return true nếu khớp
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}