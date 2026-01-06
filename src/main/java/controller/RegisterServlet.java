package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.UserBO;
import model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Cấu hình tiếng Việt
        request.setCharacterEncoding("UTF-8");
        
        // 2. Lấy dữ liệu từ form (HTML đã chặn rỗng rồi nên lấy luôn)
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // Pass thô
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullname");

        // 3. Đóng gói vào đối tượng User
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // BO sẽ lo việc mã hóa
        user.setEmail(email);
        user.setFullName(fullName);

        // 4. Gọi BO xử lý
        UserBO userBO = new UserBO();
        boolean isSuccess = userBO.registerUser(user);

        // 5. Điều hướng
        if (isSuccess) 
        {
            // Đăng ký thành công -> Chuyển sang trang login kèm thông báo
            response.sendRedirect("login.jsp?status=success");
        } 
        else 
        {
            // Đăng ký thất bại (do trùng tên hoặc lỗi khác) -> Quay lại form đăng ký
            request.setAttribute("error", "Đăng ký thất bại."+userBO.msg);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
