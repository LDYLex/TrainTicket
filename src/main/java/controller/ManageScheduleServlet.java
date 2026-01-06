package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.ScheduleBO;
import bo.StationBO;
import bo.TrainBO;
import dao.ScheduleDAO;
import dao.StationDAO;
import dao.TrainDAO;
import model.ScheduleDTO;
import model.TrainSchedule;

/**
 * Servlet implementation class ManageScheduleServlet
 */
@WebServlet(name = "ManageScheduleServlet", urlPatterns = {"/manage-schedule"})
public class ManageScheduleServlet extends HttpServlet 
{
	private ScheduleBO scheduleBO = new ScheduleBO();
    private TrainBO trainBO = new TrainBO();
    private StationBO stationBO = new StationBO();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) 
        {
            case "add":
                // Load dữ liệu Tàu và Ga để đổ vào dropdown
                request.setAttribute("trains", trainBO.getAllTrains() );
                request.setAttribute("stations", stationBO.getAllStations());
                request.getRequestDispatcher("admin-schedule-add.jsp").forward(request, response);
                break;
                
            case "delete":
                Long id = Long.parseLong(request.getParameter("id"));
                scheduleBO.deleteSchedule(id);
                response.sendRedirect("manage-schedule");
                break;
                
            default: // List
                List<ScheduleDTO> list = scheduleBO.getAllSchedules();
                request.setAttribute("schedules", list);
                request.getRequestDispatcher("admin-schedule-list.jsp").forward(request, response);
                break;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		try {
            // Lấy dữ liệu từ form thêm mới
			Long trainId = Long.parseLong(request.getParameter("trainId"));
            Long depStation = Long.parseLong(request.getParameter("depStation"));
            Long arrStation = Long.parseLong(request.getParameter("arrStation"));
            
            LocalDateTime depLDT = LocalDateTime.parse(request.getParameter("depTime"));
            LocalDateTime arrLDT = LocalDateTime.parse(request.getParameter("arrTime"));
            
            Timestamp tStart = Timestamp.valueOf(depLDT);
            Timestamp tEnd = Timestamp.valueOf(arrLDT);
            
            BigDecimal price = new BigDecimal(request.getParameter("price"));

            // --- KIỂM TRA LOGIC 1: THỜI GIAN HỢP LỆ ---
            if (!tEnd.after(tStart)) {
                 session.setAttribute("msg", "Lỗi: Thời gian Đến phải sau thời gian Đi!");
                 session.setAttribute("msgType", "danger");
                 response.sendRedirect("manage-schedule?action=add");
                 return;
            }

            // --- KIỂM TRA LOGIC 2: TÀU CÓ ĐANG CHẠY KHÔNG? ---
            if (scheduleBO.isTrainBusy(trainId, tStart, tEnd)) {
                session.setAttribute("msg", "Lỗi: Tàu này đang bận chạy một chuyến khác trong khoảng thời gian này!");
                session.setAttribute("msgType", "danger");
                response.sendRedirect("manage-schedule?action=add");
                return;
            }

            // --- KIỂM TRA LOGIC 3: TÀU CÓ ĐANG Ở ĐÚNG GA KHÔNG? ---
            String locationError = scheduleBO.checkLocationConflict(trainId, depStation, tStart);
            if (locationError != null) {
                session.setAttribute("msg", "Lỗi Logic: " + locationError);
                session.setAttribute("msgType", "danger");
                response.sendRedirect("manage-schedule?action=add");
                return;
            }

            // --- NẾU ỔN HẾT THÌ MỚI INSERT ---
            TrainSchedule ts = new TrainSchedule();
            // ... (Set các thuộc tính) ...
            ts.setTrainId(trainId);
            ts.setDepartureStationId(depStation);
            ts.setArrivalStationId(arrStation);
            ts.setDepartureTime(depLDT);
            ts.setArrivalTime(arrLDT);
            ts.setPriceAdult(price);

            boolean ok = scheduleBO.insertSchedule(ts);
            
            if (ok) {
                session.setAttribute("msg", "Lên lịch thành công!");
                session.setAttribute("msgType", "success");
            } else {
                session.setAttribute("msg", "Lỗi hệ thống khi thêm lịch.");
                session.setAttribute("msgType", "danger");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "Lỗi dữ liệu đầu vào!");
            session.setAttribute("msgType", "danger");
        }
        response.sendRedirect("manage-schedule");
        
    }


}
