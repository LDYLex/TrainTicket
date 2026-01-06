package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TrainDAO;
import model.Train;

/**
 * Servlet implementation class ManageTrainServlet
 */
@WebServlet(name = "ManageTrainServlet", urlPatterns = {"/manage-train"})
public class ManageTrainServlet extends HttpServlet {
	private TrainDAO trainDAO = new TrainDAO();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageTrainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                // Chuyển sang trang form thêm mới
                request.getRequestDispatcher("admin-train-add.jsp").forward(request, response);
                break;
                
            case "delete":
                // Xử lý xóa tàu
                deleteTrain(request, response);
                break;
                
            default:
                // Mặc định: Lấy danh sách và hiện trang quản lý
                List<Train> list = trainDAO.getAllTrains();
                request.setAttribute("trains", list);
                request.getRequestDispatcher("admin-train-list.jsp").forward(request, response);
                break;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // 1. Lấy dữ liệu từ form
            String code = request.getParameter("trainCode");
            String name = request.getParameter("trainName");
            int numCoaches = Integer.parseInt(request.getParameter("numCoaches"));
            int seatsPerCoach = Integer.parseInt(request.getParameter("seatsPerCoach"));

            Train t = new Train(null, code, name);

            // 2. Gọi hàm INSERT mới (kèm toa và ghế)
            // Lưu ý: Đổi tên hàm insertTrain -> insertTrainWithDetails
            boolean isSuccess = trainDAO.insertTrainWithDetails(t, numCoaches, seatsPerCoach);

            HttpSession session = request.getSession();
            if (isSuccess) {
                session.setAttribute("msg", "Thêm tàu thành công (Đã tạo " + numCoaches + " toa và " + (numCoaches * seatsPerCoach) + " ghế)!");
                session.setAttribute("msgType", "success");
            } else {
                session.setAttribute("msg", "Lỗi! Có thể mã tàu bị trùng hoặc lỗi hệ thống.");
                session.setAttribute("msgType", "danger");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("manage-train");
	}
	private void deleteTrain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            
            // Gọi hàm xóa thông minh mới
            int status = trainDAO.deleteTrainSmart(id);
            
            HttpSession session = request.getSession();
            if (status == 1) {
                session.setAttribute("msg", "Đã xóa tàu và toàn bộ toa/ghế đi kèm thành công!");
                session.setAttribute("msgType", "success");
            } else if (status == -1) {
                session.setAttribute("msg", "CẢNH BÁO: Không thể xóa tàu này vì đang có lịch trình hoạt động!");
                session.setAttribute("msgType", "warning"); // Màu vàng cảnh báo
            } else {
                session.setAttribute("msg", "Lỗi hệ thống! Không thể xóa.");
                session.setAttribute("msgType", "danger");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("manage-train");
    }

}
