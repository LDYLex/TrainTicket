package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.UserBO;
import model.User;
import nl.captcha.Captcha;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// 1. Lấy dữ liệu
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        HttpSession session = request.getSession();
        
        
        Integer lansai= (Integer) session.getAttribute("solansai");
		if(lansai==null) 
		{
			lansai=0;
			session.setAttribute("solansai", lansai);
		}
		try 
		{
			System.out.println(lansai);
            // Nếu người dùng chưa submit form, chỉ hiển thị trang login
            if (u == null || p == null) 
            {
            	request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            // (lansai >=3), kiểm tra captcha trước
            if (lansai >= 3) 
            {
                Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
                String answer = request.getParameter("answer");
                if (answer == null || captcha == null || !captcha.isCorrect(answer)) 
                {
                    request.setAttribute("showCaptcha", true);
                    request.setAttribute("Dn", "Captcha không đúng!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }
            // 2. Gọi BO kiểm tra (Check pass mã hóa bên trong BO)
            UserBO userBO = new UserBO();
            User user = userBO.checkLogin(u, p);

            // 3. Xử lý kết quả
            if (user != null) 
            {
                // --- ĐĂNG NHẬP THÀNH CÔNG ---
                
                // Tạo session để lưu phiên làm việc
            	session.setAttribute("solansai", 0);
                session.setAttribute("user", user); // Lưu cả object User vào session
                

                // Phân quyền chuyển hướng
                if ("ADMIN".equalsIgnoreCase(user.getRole())) 
                {
                    response.sendRedirect("admin.jsp"); // Trang quản trị
                } 
                else 
                {
                	 request.getRequestDispatcher("home.jsp").forward(request, response); // Trang chủ khách hàng
                }
                
            } 
            else 
            {
                // --- ĐĂNG NHẬP THẤT BẠI ---
            	lansai++;
                session.setAttribute("solansai", lansai);
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                if (lansai >= 3) 
                {
                    request.setAttribute("showCaptcha", true);
                }
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
		}
		catch (Exception e) 
		{
            e.printStackTrace();
            request.setAttribute("Dn", "Tên đăng nhập hoặc mật khẩu không đúng!");
            lansai++;
            session.setAttribute("solansai", lansai);
            request.getRequestDispatcher("login.jsp").forward(request, response);
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
