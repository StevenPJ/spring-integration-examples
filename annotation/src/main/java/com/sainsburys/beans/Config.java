package com.sainsburys.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@ComponentScan
public class Config {

    @Bean MessageChannel json_to_data() { return new DirectChannel(); }
    @Bean MessageChannel data_router() { return new DirectChannel(); }
    @Bean MessageChannel data_filter() { return new DirectChannel(); }
    @Bean MessageChannel foo_sender() { return new DirectChannel(); }
    @Bean MessageChannel foo_json_out() { return new DirectChannel(); }
    @Bean MessageChannel bar_sender() { return new DirectChannel(); }
    @Bean MessageChannel bar_json_out() { return new DirectChannel(); }
    @Bean MessageChannel foo_out() { return new QueueChannel(); }
    @Bean MessageChannel bar_out() { return new QueueChannel(); }

    @Bean
    @Transformer(inputChannel = "json_to_data", outputChannel = "data_filter")
    JsonToObjectTransformer jsonToDataTransformer() {
        return new JsonToObjectTransformer(Data.class);
    }

    @Bean
    @Transformer(inputChannel = "foo_json_out", outputChannel = "foo_out")
    ObjectToJsonTransformer fooToJsonTransformer() {
        return new ObjectToJsonTransformer();
    }

    @Bean
    @Transformer(inputChannel = "bar_json_out", outputChannel = "bar_out")
    ObjectToJsonTransformer barToJsonTransformer() {
        return new ObjectToJsonTransformer();
    }

}
