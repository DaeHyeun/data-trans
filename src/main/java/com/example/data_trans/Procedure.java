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
import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Procedure implements Runnable{
    private String name;
    private String message;
    private String receivedId;
    private HashMap<String, Object> mapData;
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

            // Create the destination
            Destination destination = session.createQueue(receivedId);

            // Create a MessageProducer from the Session to the Queue
            MessageProducer producer = session.createProducer(destination);
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
            } else if(mapData != null){
                // If a message is provided, send a MapMessage
                MapMessage mapMessage = session.createMapMessage();
                for (Map.Entry<String, Object> entry : mapData.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        mapMessage.setString(entry.getKey(), (String) entry.getValue()); // Add key-value pairs
                    } else if (entry.getValue() instanceof Integer) {
                        mapMessage.setInt(entry.getKey(), Integer.parseInt(entry.getKey()));
                    } else if (entry.getValue() instanceof Character) {
                        mapMessage.setChar(entry.getKey(), (Character) entry.getValue());
                    }
                }
                // Send the MapMessage
                producer.send(mapMessage); // Send the MapMessage
            }else {
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
