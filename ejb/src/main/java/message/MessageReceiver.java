package message;

import com.google.gson.Gson;
import dto.Gps;
import dto.TrafficDataDto;
import ejb.remote.TrafficData;
import entity.GpsEnitiy;
import entity.TrafficDataEntity;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.LockingObject;

import javax.naming.InitialContext;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "MyQueue")
        }
)
public class MessageReceiver implements MessageListener {
    @Override
    public void onMessage(Message message) {

        try {
            synchronized (LockingObject.class) {
                Session ses = null;

                Gson gson = new Gson();
                TrafficDataDto trdto = gson.fromJson(message.getBody(String.class), TrafficDataDto.class);

                SessionFactory sf = HibernateUtil.getSessionFactory();

                ses = sf.openSession();
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
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
