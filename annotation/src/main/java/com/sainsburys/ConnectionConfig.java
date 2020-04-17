package com.sainsburys;

import com.sainsburys.beans.Config;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.DefaultJmsHeaderMapper;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.jms.ConnectionFactory;

@Configuration
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

    @Bean
    public JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint(ConnectionFactory connectionFactory, MessageChannel json_to_data) {
        JmsMessageDrivenEndpoint endpoint = new JmsMessageDrivenEndpoint(
                simpleMessageListenerContainer(connectionFactory),
                channelPublishingJmsMessageListener());
        endpoint.setOutputChannel(json_to_data);
        return endpoint;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(
            ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName("data.in");
        return container;
    }

    @Bean
    public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
        return new ChannelPublishingJmsMessageListener();
    }

    @Bean
    @ServiceActivator(inputChannel = "foo_out")
    public MessageHandler fooAdapter(JmsTemplate jmsTemplate) {
        JmsSendingMessageHandler handler = new JmsSendingMessageHandler(jmsTemplate);
        handler.setDestinationName("foo.topic");
        return handler;
    }

    @Bean
    @ServiceActivator(inputChannel = "bar_out")
    public MessageHandler barAdapter(JmsTemplate jmsTemplate) {
        JmsSendingMessageHandler handler = new JmsSendingMessageHandler(jmsTemplate);
        handler.setDestinationName("bar.queue");
        return handler;
    }
}
