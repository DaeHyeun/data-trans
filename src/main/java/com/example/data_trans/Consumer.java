package com.example.data_trans;

import jakarta.jms.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Consumer implements Runnable, ExceptionListener{
    private String name;
    private String receivedId;

    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Destination
            Destination destination = session.createQueue(name);

            // Create a MessageConsumer from the Session to the Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Continuously listen for new messages
            while (true) {
                Message message = consumer.receive(); // Wait indefinitely for a new message
                if (message instanceof TextMessage) {
                    // Handle text message
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println(receivedId + " : " + text);
                } else if (message instanceof BytesMessage) {

                    // Handle file (BytesMessage)
                    BytesMessage bytesMessage = (BytesMessage) message;
                    byte[] fileBytes = new byte[(int) bytesMessage.getBodyLength()];
                    bytesMessage.readBytes(fileBytes);

                    // Save the file to the specified location
                    File outputFile = new File("D:\\download", "받은파일" + System.currentTimeMillis() + ".txt");
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        fos.write(fileBytes);
                            System.out.println("파일전송 및 저장 : " + outputFile.getAbsolutePath());
                        } catch (IOException e) {
                            System.out.println("Error saving received file: " + e);
                        }

                } else if (message instanceof MapMessage) {
                    // Handle MapMessage
                    MapMessage mapMessage = (MapMessage) message;
                    HashMap<String, Object> mapData = new HashMap<>();

                    // Extract data from the MapMessage and store it in the HashMap
                    for (Enumeration<?> e = mapMessage.getMapNames(); e.hasMoreElements(); ) {
                        String key = (String) e.nextElement();
                        Object value = null;

                        // Depending on the type of value, get the corresponding value from the MapMessage
                        try {
                            if (mapMessage.itemExists(key)) {
                                if (mapMessage.getObject(key) instanceof String) {
                                    value = mapMessage.getString(key);
                                } else if (mapMessage.getObject(key) instanceof Integer) {
                                    value = mapMessage.getInt(key);
                                } else if (mapMessage.getObject(key) instanceof Boolean) {
                                    value = mapMessage.getBoolean(key);
                                } else if (mapMessage.getObject(key) instanceof Double) {
                                    value = mapMessage.getDouble(key);
                                } else {
                                    value = mapMessage.getObject(key); // For other types
                                }
                                mapData.put(key, value); // Add the key-value pair to the map

                            }
                        } catch (JMSException e1) {
                            e1.printStackTrace();
                        }
                    }
                    System.out.println(mapData);
                } else {
                    System.out.println(name + " received an unexpected message type.");
                }
            }
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured. Shutting down client.");
    }
}
