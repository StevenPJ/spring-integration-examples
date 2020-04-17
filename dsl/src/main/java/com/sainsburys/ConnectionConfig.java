package com.sainsburys;

import com.sainsburys.beans.Config;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.DefaultJmsHeaderMapper;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.integration.jms.dsl.Jms;
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
        return new ActiveMQConnectionFactory("vm://embedded-broker?broker.persistent=false");
    }

    @Bean
    public IntegrationFlow jmsInboundFlow(ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("data.in"))
                .channel("json_to_data")
                .get();
    }

    @Bean
    public IntegrationFlow barOutbound(ConnectionFactory connectionFactory) {
        return IntegrationFlows.from("bar_out")
                .handle(Jms.outboundAdapter(connectionFactory).destination("bar.queue"))
                .get();
    }


    @Bean
    public IntegrationFlow fooOutbound(ConnectionFactory connectionFactory) {
        return IntegrationFlows.from("foo_out")
                .handle(Jms.outboundAdapter(connectionFactory).destination("foo.topic"))
                .get();
    }
}
