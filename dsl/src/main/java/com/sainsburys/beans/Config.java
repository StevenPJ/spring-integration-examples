package com.sainsburys.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Transformers;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@ComponentScan
public class Config {

    @Bean MessageChannel json_to_data() { return MessageChannels.direct().get(); }
    @Bean MessageChannel foo_sender() { return MessageChannels.direct().get(); }
    @Bean MessageChannel bar_sender() { return MessageChannels.direct().get(); }
    @Bean MessageChannel foo_out() { return MessageChannels.queue().get(); }
    @Bean MessageChannel bar_out() { return MessageChannels.queue().get(); }

    @Bean
    IntegrationFlow main() {
        return IntegrationFlows.from("json_to_data")
                .transform(Transformers.fromJson(Data.class))
                .filter((Data data) -> true)
                .routeToRecipients(r -> r
                        .recipient("foo_sender")
                        .recipient("bar_sender")
                )
                .get();
    }

    @Bean
    IntegrationFlow foo() {
        return IntegrationFlows.from("foo_sender")
                .transform((Data data) -> new Foo(data.getValue()))
                .transform(Transformers.toJson())
                .channel("foo_out")
                .get();
    }

    @Bean
    IntegrationFlow bar() {
        return IntegrationFlows.from("bar_sender")
                .transform((Data data) -> new Bar(data.getValue()))
                .transform(Transformers.toJson())
                .channel("bar_out")
                .get();
    }


}
