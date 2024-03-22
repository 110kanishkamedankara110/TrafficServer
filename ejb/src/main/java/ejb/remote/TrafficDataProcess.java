package ejb.remote;

import dto.AverageData;
import dto.Gps;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface TrafficDataProcess {
    public List<AverageData> getAverage();
    public AverageData getAverage(Gps location);
    public int getAllAverage();
    public List<AverageData> getMaxSpeeds();
    public AverageData getMaxSpeeds(Gps location);
    public AverageData getMaxAllSpeeds();
    public List<AverageData> getMinSpeeds();
    public AverageData getMinSpeeds(Gps location);
    public AverageData getMinAllSpeeds();

}
