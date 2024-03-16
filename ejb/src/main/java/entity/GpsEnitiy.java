package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GpsEnitiy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String latitude;
    private String longitude;

    @OneToMany(mappedBy = "gps")
    List<TrafficDataEntity> trafficData=new LinkedList<>();

    public int getId() {
        return id;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<TrafficDataEntity> getTrafficData() {
        return trafficData;
    }

    public void setTrafficData(TrafficDataEntity trafficData) {
        this.trafficData.add(trafficData);
    }
}
