package bo;

import java.util.List;

import dao.StationDAO;
import model.Station;

public class StationBO {
	StationDAO stationdao= new StationDAO();
	 public List<Station> getAllStations() {
		 return stationdao.getAllStations();
	 }
	 public Station getStationById(Long id) {
		 return stationdao.getStationById(id);
	 }
	 
}
