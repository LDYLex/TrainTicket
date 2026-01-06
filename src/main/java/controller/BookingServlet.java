package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.ScheduleBO;
import dao.SeatDAO;
import model.CoachDTO;
import model.ScheduleDTO;
import model.User;

/**
 * Servlet implementation class BookingServlet
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. KIỂM TRA ĐĂNG NHẬP (Bắt buộc)
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); // Giả sử bạn lưu object User vào session khi login
        
        if (user == null) 
        {
            // Lưu lại trang hiện tại để login xong quay lại (Optional)
            String currentQuery = request.getQueryString();
           
            
            // Chuyển hướng sang trang đăng nhập
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Lấy thông tin chuyến tàu khách chọn
		String scheduleIdRaw = request.getParameter("scheduleId");
		        
		if (scheduleIdRaw != null) 
		{
			    try 
			    {
			        Long scheduleId = Long.parseLong(scheduleIdRaw);
			        
			        // 1. GỌI BO ĐỂ LẤY THÔNG TIN
			        ScheduleBO scheduleBO = new ScheduleBO();
			        ScheduleDTO schedule = scheduleBO.getScheduleById(scheduleId);
			        
			        // Đẩy sang JSP để hiển thị: "Hà Nội -> Sài Gòn, 08:00..."
			        request.setAttribute("schedule", schedule);
			
			        // 2. LẤY DANH SÁCH GHẾ (Phần này bạn đã làm rồi)
			        SeatDAO seatDAO = new SeatDAO();
			        List<CoachDTO> coaches = seatDAO.getCoachesAndSeats(scheduleId);
			        request.setAttribute("coaches", coaches);
			        
			    } 
			    catch (NumberFormatException e) 
			    {
		                response.sendRedirect("home"); // Lỗi ID thì về trang chủ
		                return;
		         }
		 } 
		else {
		            response.sendRedirect("home");
		            return;
		        }
		
		   request.getRequestDispatcher("booking.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
