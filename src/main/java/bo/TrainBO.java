package bo;

import java.util.List;

import dao.TrainDAO;
import model.Train;

public class TrainBO {
	TrainDAO traindao= new TrainDAO();
	 public List<Train> getAllTrains() 
	 {
		 return traindao.getAllTrains();
	 }
	 public boolean insertTrainWithDetails(Train t, int numCoaches, int seatsPerCoach) {
		 return traindao.insertTrainWithDetails(t, numCoaches, seatsPerCoach);
	 }
	 public int deleteTrainSmart(Long trainId) 
	    {
		 return traindao.deleteTrainSmart(trainId);
	    }
}
