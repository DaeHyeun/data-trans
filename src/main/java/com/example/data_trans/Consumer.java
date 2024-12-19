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

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Consumer implements Runnable, ExceptionListener{
    private String name;
    private String cusTopic ;

    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Destination changed to Topic
            Topic topic = session.createTopic(cusTopic);

            // Create a MessageConsumer from the Session to the Topic
            MessageConsumer consumer = session.createConsumer(topic);

            // Continuously listen for new messages
            while (true) {
                Message message = consumer.receive(); // Wait indefinitely for a new message
                if (message instanceof TextMessage) {
                    // Handle text message
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println(text);
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
