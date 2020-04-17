package com.sainsburys.adapter;

import com.sainsburys.Bar;
import com.sainsburys.Foo;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import java.util.function.Consumer;

@Configuration
public class ConnectionConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMq = new ActiveMQConnectionFactory("tcp://golf-mq:61616");
        activeMq.setUserName("admin");
        activeMq.setPassword("admin");
        activeMq.setTrustAllPackages(true);
        return activeMq;
    }

    @Bean
    @Autowired
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    Consumer<Foo> fooAdapter(JmsTemplate jmsTemplate) {
        return foo -> jmsTemplate.convertAndSend("foo.queue", foo);
    }

    @Bean
    Consumer<Bar> barAdapter(JmsTemplate jmsTemplate) {
        return bar -> jmsTemplate.convertAndSend("bar.topic", bar);
    }
}

