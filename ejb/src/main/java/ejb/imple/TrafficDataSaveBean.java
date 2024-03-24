package ejb.imple;

import dto.TrafficDataDto;
import ejb.remote.TrafficDataSave;
import entity.GpsEnitiy;
import entity.TrafficDataEntity;
import jakarta.ejb.Singleton;
import message.MessageSender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

@Singleton
public class TrafficDataSaveBean implements TrafficDataSave {
    public void save(TrafficDataDto trdto){
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session ses = sf.openSession();
        Transaction ta = ses.getTransaction();
        GpsEnitiy ge = null;
        Query<GpsEnitiy> que = ses.createQuery("SELECT g FROM GpsEnitiy g WHERE g.latitude=:lat AND g.longitude=:lon", GpsEnitiy.class);
        que.setParameter("lat", trdto.getGps().getLatitude());
        que.setParameter("lon", trdto.getGps().getLongitude());


        if (!que.getResultList().isEmpty()) {
            ge = que.getSingleResult();
        } else {
            ge = new GpsEnitiy();
            ge.setLatitude(trdto.getGps().getLatitude());
            ge.setLongitude(trdto.getGps().getLongitude());
        }

        TrafficDataEntity td = new TrafficDataEntity();

        td.setGps(ge);
        td.setSpeed(trdto.getSpeed());
        td.setTrafficLight(trdto.getTrafficLight());

        ge.setTrafficData(td);


        try {
            ta.begin();
            ses.merge(td);
            ta.commit();
        } catch (Exception ex) {
            ta.rollback();
            ex.printStackTrace();
        } finally {
            ses.close();
        }
        MessageSender.sendMessage();
    }
}
