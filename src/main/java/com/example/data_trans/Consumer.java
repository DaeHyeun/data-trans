package com.example.data_trans;

import jakarta.jms.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Consumer implements Runnable, ExceptionListener{

    private String type;
    private String name;


    public void run() {
        try {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();

            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println(connection.getClientID());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        connection.setClientID(name);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println(connection.getClientID());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        connection.start();
        connection.setExceptionListener(this);

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        if(type.equals("queue")){
            Destination destination = session.createQueue(name);
            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);
            // Wait for a message
            Message message = consumer.receive(1000);
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("type : " + type + "\nname : " + name + "\nmessage : " + text);
            } else {
                System.out.println("type : " + type + "\nname : " + name );
            }
            consumer.close();

        }else{
            Topic destination = session.createTopic(name);
            MessageConsumer consumer = session.createDurableSubscriber(destination,name);
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("type : " + type + "\nname : " + name + "\nmessage : " + text);
            } else {
                System.out.println("type : " + type + "\nname : " + name );
            }
            consumer.close();
        }
            session.close();
            System.out.println("세션 클로즈 ");
            connection.close();
            System.out.println("커넥션 클로즈 ");
    } catch (Exception e) {
        System.out.println("Caught: " + e);
        e.printStackTrace();
    }
}
    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }

}
