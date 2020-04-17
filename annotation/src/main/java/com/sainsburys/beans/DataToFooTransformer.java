package com.sainsburys.beans;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;

@MessageEndpoint
public class DataToFooTransformer {

    @Transformer(inputChannel = "foo_sender", outputChannel = "foo_json_out")
    public Foo transform(Data data) {
        return new Foo(data.getValue());
    }
}
