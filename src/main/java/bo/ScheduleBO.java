package bo;

import dao.ScheduleDAO;
import dao.StationDAO;
import model.ScheduleDTO;
import model.Station;
import model.TrainSchedule;

import java.sql.Timestamp;
import java.util.List;

public class ScheduleBO {

    private ScheduleDAO scheduleDAO = new ScheduleDAO();
    private StationDAO stationDAO = new StationDAO();

    // 1. Lấy lịch trình có lọc
    public List<ScheduleDTO> searchSchedules(String fromIdStr, String toIdStr) 
    {
        Long fromId = null;
        Long toId = null;

        // Chuyển đổi String sang Long an toàn
        try {
            if (fromIdStr != null && !fromIdStr.isEmpty()) fromId = Long.parseLong(fromIdStr);
            if (toIdStr != null && !toIdStr.isEmpty()) toId = Long.parseLong(toIdStr);
        } catch (NumberFormatException e) {
            // Log lỗi nếu cần, hoặc bỏ qua
        }

        return scheduleDAO.searchSchedules(fromId, toId);
    }

    // 2. Lấy danh sách tất cả các ga để hiển thị lên Dropdown
    public List<Station> getAllStations() {
        return stationDAO.getAllStations();
    }
 // Lấy thông tin chi tiết chuyến tàu để hiển thị lên trang Đặt vé
    public ScheduleDTO getScheduleById(Long id) {
        return scheduleDAO.getScheduleById(id);
    }
    public boolean insertSchedule(TrainSchedule ts) 
    {
    	return scheduleDAO.insertSchedule(ts);
    }
    public boolean deleteSchedule(Long id) 
    {
    	return scheduleDAO.deleteSchedule(id);
    }
    public List<ScheduleDTO> getAllSchedules()
    {
    	return scheduleDAO.getAllSchedules();
    }
    public boolean isTrainBusy(Long trainId, Timestamp newStart, Timestamp newEnd)
    {
    	return scheduleDAO.isTrainBusy(trainId, newStart, newEnd);
    }
    public String checkLocationConflict(Long trainId, Long newDepStationId, Timestamp newStart)
    {
    	return scheduleDAO.checkLocationConflict(trainId, newDepStationId, newStart);
    }
}