package message;

import com.google.gson.Gson;
import dto.TrafficDataDto;
import ejb.imple.TrafficDataSaveBean;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import util.LockingObject;

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

                Gson gson = new Gson();
                TrafficDataDto trdto = gson.fromJson(message.getBody(String.class), TrafficDataDto.class);
                TrafficDataSaveBean tdab = new TrafficDataSaveBean();
                tdab.save(trdto);

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
