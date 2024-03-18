package web.util;

import com.google.gson.Gson;
import dto.TrafficDataDto;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.List;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "inform")
        }
)
public class MessageReciver implements jakarta.jms.MessageListener {
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
