package com.sainsburys.beans;

import org.springframework.stereotype.Component;

@Component
public class DataToBarTransformer {

    public Bar transform(Data data) {
        return new Bar(data.getValue());
    }
}
