package ejb.imple;

import dto.Gps;
import dto.TrafficDataDto;
import ejb.remote.TrafficData;
import entity.GpsEnitiy;
import entity.TrafficDataEntity;
import jakarta.ejb.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.LinkedList;
import java.util.List;

@Singleton
public class TrafficDataBean implements TrafficData {
    @Override
    public List<TrafficDataDto> getData() {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        List<TrafficDataEntity> tde=session.createQuery("SELECT t FROM TrafficDataEntity t", TrafficDataEntity.class).getResultList();

        List<TrafficDataDto> trafficDataDtos=new LinkedList<>();

        tde.forEach((data)->{
            Gps gps=new Gps();
            TrafficDataDto trafficDataDto=new TrafficDataDto();

            gps.setLongitude(data.getGps().getLongitude());
            gps.setLatitude(data.getGps().getLatitude());

            trafficDataDto.setGps(gps);
            trafficDataDto.setTrafficLight(data.getTrafficLight());
            trafficDataDto.setSpeed(data.getSpeed());

            trafficDataDtos.add(trafficDataDto);
        });



        return trafficDataDtos;

    }

    @Override
    public List<TrafficDataDto> getData(String latitude,String longitude) {


        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        List<TrafficDataEntity> tde=session.createQuery("SELECT t FROM TrafficDataEntity t WHERE t.gps.longitude=:lon AND t.gps.latitude=:lat", TrafficDataEntity.class).setParameter("lat",latitude).setParameter("lon",longitude).getResultList();

        List<TrafficDataDto> trafficDataDtos=new LinkedList<>();

        tde.forEach((data)->{
            Gps gps=new Gps();
            TrafficDataDto trafficDataDto=new TrafficDataDto();

            gps.setLongitude(data.getGps().getLongitude());
            gps.setLatitude(data.getGps().getLatitude());

            trafficDataDto.setGps(gps);
            trafficDataDto.setTrafficLight(data.getTrafficLight());
            trafficDataDto.setSpeed(data.getSpeed());

            trafficDataDtos.add(trafficDataDto);
        });

        return trafficDataDtos;
    }

    @Override
    public List<Gps> getLocations() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        List<GpsEnitiy> locationsEntity = session.createQuery("SELECT g FROM GpsEnitiy g", GpsEnitiy.class).getResultList();
        List<Gps> locations = new LinkedList<>();

        locationsEntity.forEach((data) -> {
            Gps gps=new Gps();
            gps.setLatitude(data.getLatitude());
            gps.setLongitude(data.getLongitude());
            locations.add(gps);
        });


        return locations;
    }

}
