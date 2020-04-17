package com.sainsburys.beans;

import org.springframework.stereotype.Component;

@Component
public class DataFilter {

    public boolean filter(Data data) {
        return true;
    }
}
