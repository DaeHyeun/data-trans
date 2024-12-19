package com.example.data_trans;

import jakarta.jms.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Procedure implements Runnable{
    private String name;
    private String cusTopic;
    private String message;
    private File file; // To hold the file to be sent

    @Override
    public void run() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination as Topic (Changed from Queue)
            Topic topic = session.createTopic(cusTopic);

            // Create a MessageProducer from the Session to the Topic
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            if (file != null) {
                // If a file is provided, send it as a BytesMessage
                BytesMessage bytesMessage = session.createBytesMessage();
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] fileBytes = new byte[(int) file.length()];
                    fileInputStream.read(fileBytes);
                    bytesMessage.writeBytes(fileBytes);
                }
                producer.send(bytesMessage); // Send the file as a BytesMessage
            } else {
                // If no file, send a regular TextMessage
                TextMessage textMessage = session.createTextMessage(message);
                producer.send(textMessage); // Send the text message
            }

            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
