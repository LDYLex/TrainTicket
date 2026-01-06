package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.ScheduleBO;
import model.ScheduleDTO;
import model.Station;

/**
 * Servlet implementation class ScheduleServlet
 */
@WebServlet(name = "ScheduleServlet", urlPatterns = {"/schedule"})
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleBO bo = new ScheduleBO();

        // 1. Lấy tham số từ form lọc (nếu có)
        String fromStation = request.getParameter("fromStation");
        String toStation = request.getParameter("toStation");

        // 2. Lấy danh sách lịch trình theo điều kiện lọc
        List<ScheduleDTO> schedules = bo.searchSchedules(fromStation, toStation);
        
        // 3. Lấy danh sách Ga để đổ vào thẻ <select>
        List<Station> stations = bo.getAllStations();

        // 4. Gửi dữ liệu sang JSP
        request.setAttribute("schedules", schedules);
        request.setAttribute("stations", stations);
        request.getRequestDispatcher("schedule.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
