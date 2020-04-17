package com.sainsburys.beans;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;

@MessageEndpoint
public class DataToBarTransformer {

    @Transformer(inputChannel = "bar_sender", outputChannel = "bar_json_out")
    public Bar transform(Data data) {
        return new Bar(data.getValue());
    }
}
