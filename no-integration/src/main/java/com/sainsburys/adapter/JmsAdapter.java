package com.sainsburys.adapter;

import com.sainsburys.Data;
import com.sainsburys.DataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JmsAdapter {

    private final DataProcessor dataProcessor;

    @JmsListener(destination = "data.in")
    void process(Data data) {
        dataProcessor.process(data);
    }
}
