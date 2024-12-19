package config;

import com.example.data_trans.queue.MessageDto;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.broker-url}")
    private String activemqBrokerUrl;//tcp://localhost:61616

    @Value("${spring.activemq.user}")
    private String activemqUsername;//admin

    @Value("${spring.activemq.password}")
    private String activemqPassword;//admin

    //팩토리 생성
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(activemqBrokerUrl);
        activeMQConnectionFactory.setUserName(activemqUsername);
        activeMQConnectionFactory.setPassword(activemqPassword);
        activeMQConnectionFactory.setClientID("dh");
        return activeMQConnectionFactory;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //jms queue template
    @Bean
    public JmsTemplate jmsTemplateQueue() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setExplicitQosEnabled(true);    // 메시지 전송 시 QOS을 설정
        jmsTemplate.setDeliveryPersistent(false);   // 메시지의 영속성을 설정
        jmsTemplate.setReceiveTimeout(1000 * 3);    // 메시지를 수신하는 동안의 대기 시간을 설정(3초)
        jmsTemplate.setTimeToLive(1000 * 60 * 30);  // 메시지의 유효 기간을 설정(30분)
        return jmsTemplate;
    }

    //queue container
    @Bean
    public JmsListenerContainerFactory<?> containerFactoryQueue() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //jms topic template
    @Bean
    public JmsTemplate jmsTemplateTopic() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setExplicitQosEnabled(true);    // 메시지 전송 시 QOS을 설정
        jmsTemplate.setDeliveryPersistent(false);   // 메시지의 영속성을 설정
        jmsTemplate.setReceiveTimeout(1000 * 3);    // 메시지를 수신하는 동안의 대기 시간을 설정(3초)
        jmsTemplate.setTimeToLive(1000 * 60);       // 메시지의 유효 기간을 설정(1분)
        return jmsTemplate;
    }

    //topic container
    @Bean
    public JmsListenerContainerFactory<?> containerFactoryTopic() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //컨버터
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("message", MessageDto.class);
        //나중에 Object.class로 변경예정
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }


}