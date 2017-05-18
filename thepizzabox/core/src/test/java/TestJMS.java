

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;

public class TestJMS implements Runnable {
	@Test
    public void run() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");


            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("order.queue");

            // Create a MessageProducer from the Session to the Topic or
            // Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            User user = new User();
            user.setUsername("Rupali");
            user.setAddress("Bavdhan");
            Order order = new Order(1, PaymentType.CASH, Status.READY, null, 1000.0, user, null, null);
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(order);
            // Tell the producer to send the message
            System.out.println("Sent: " + objectMessage );
            producer.send(objectMessage);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
