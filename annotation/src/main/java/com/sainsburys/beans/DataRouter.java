package com.sainsburys.beans;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

import java.util.Arrays;
import java.util.List;

@MessageEndpoint
public class DataRouter {

    @Router(inputChannel = "data_router")
    public List<String> route(Data data) {
        return Arrays.asList("foo_sender", "bar_sender");
    }
}
