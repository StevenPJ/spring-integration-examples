package com.sainsburys.beans;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;

@MessageEndpoint
public class DataFilter {

    @Filter(inputChannel = "data_filter", outputChannel = "data_router")
    public boolean filter(Data data) {
        return true;
    }
}
