package ejb.remote;

import dto.Gps;
import dto.TrafficDataDto;
import entity.GpsEnitiy;
import entity.TrafficDataEntity;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TrafficData {
    public List<TrafficDataDto> getData();
    public List<TrafficDataDto> getData(String latitude,String longitude);
    public List<Gps> getLocations();
}
