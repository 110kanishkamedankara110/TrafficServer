package message;

import dto.Gps;
import dto.TrafficDataDto;
import ejb.imple.TrafficDataBean;
import jakarta.jms.*;

import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageSender {
static final QueueSender sender;
static final QueueSession session;
    static {
        try {
            InitialContext ic;

            ic = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ic.lookup("TrafficQueue");
            QueueConnection connection = factory.createQueueConnection();
            connection.start();

            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue que = (Queue) ic.lookup("inform");
            sender = session.createSender(que);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(){
        try {
            List<TrafficDataDto> data= new TrafficDataBean().getData();

            ArrayList<TrafficDataDto> d=new ArrayList();

           data.forEach((ele)->{
               Gps gps=new Gps();
               gps.setLatitude(ele.getGps().getLatitude());
               gps.setLongitude(ele.getGps().getLongitude());
               TrafficDataDto tr=new TrafficDataDto();
               tr.setGps(gps);
               tr.setSpeed(ele.getSpeed());
               tr.setTrafficLight(ele.getTrafficLight());
               d.add(tr);
            });



            ObjectMessage mes=session.createObjectMessage(d);

            sender.send(mes);

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    };


}
