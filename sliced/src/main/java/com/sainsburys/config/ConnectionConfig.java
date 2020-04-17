package com.sainsburys.config;

import com.sainsburys.beans.Config;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.jms.DefaultJmsHeaderMapper;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@ImportResource(locations = {"classpath:/adapters.xml"})
@Import(Config.class)
public class ConnectionConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMq = new ActiveMQConnectionFactory("vm://embedded-broker?broker.persistent=false");
        activeMq.setTrustAllPackages(true);
        return activeMq;
    }

    @Bean
    @Autowired
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public DefaultJmsHeaderMapper jmsHeaderMapper() {
        return new DefaultJmsHeaderMapper();
    }
}
