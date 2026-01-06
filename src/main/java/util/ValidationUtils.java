package util;

import java.util.regex.Pattern;

public class ValidationUtils {
    // Regex chuẩn để check email
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Kiểm tra email có đúng định dạng không
     * @param email chuỗi email cần kiểm tra
     * @return true nếu đúng định dạng
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
}