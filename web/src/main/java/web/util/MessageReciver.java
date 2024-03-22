package web.util;

import com.google.gson.Gson;
import dto.TrafficDataDto;
import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;

import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.List;


public class MessageReciver implements jakarta.jms.MessageListener {

    private final QueueReceiver receiver;

    private final QueueConnectionFactory factory ;
    private final Queue que;
    public MessageReciver(){
        try {
            InitialContext ic=new InitialContext();
            factory=(QueueConnectionFactory)ic.lookup("TrafficQueue");
            que=(Queue) ic.lookup("inform");
            QueueConnection connection=factory.createQueueConnection();
            connection.start();
            QueueSession session=connection.createQueueSession(true, jakarta.jms.Session.AUTO_ACKNOWLEDGE);
            receiver=session.createReceiver(que);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public void startMessageListener(){
        try {
            receiver.setMessageListener(this);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            ArrayList<TrafficDataDto> data=message.getBody(ArrayList.class);

            List<Session> sessions=ServerClients.getClients();
            sessions.forEach((session)->{
               if(session.isOpen()){
                    RemoteEndpoint.Async asyncRemote = session.getAsyncRemote();
                    asyncRemote.sendText(new Gson().toJson(data));
                }else{
                   sessions.remove(session);
               }

            });

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
