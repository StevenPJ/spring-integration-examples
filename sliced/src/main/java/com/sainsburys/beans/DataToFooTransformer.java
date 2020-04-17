package com.sainsburys.beans;

import org.springframework.stereotype.Component;

@Component
public class DataToFooTransformer {

    public Foo transform(Data data) {
        return new Foo(data.getValue());
    }
}
