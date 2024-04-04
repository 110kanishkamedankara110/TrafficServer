package ejb.remote;

import dto.TrafficDataDto;
import jakarta.ejb.Remote;

@Remote
public interface TrafficDataSave {
    public void save(TrafficDataDto trdto);
}
