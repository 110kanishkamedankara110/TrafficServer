package entity;

import jakarta.persistence.*;

@Entity
public class TrafficDataEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int speed;
    private int trafficLight;

    @ManyToOne(cascade = CascadeType.ALL)
    private GpsEnitiy gps;

    public int getId() {
        return id;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getTrafficLight() {
        return trafficLight;
    }
    public void setTrafficLight(int trafficLight) {
        this.trafficLight = trafficLight;
    }
    public GpsEnitiy getGps() {
        return gps;
    }

    public void setGps(GpsEnitiy gps) {
        this.gps = gps;
    }
}

