package com.sainsburys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:/leakyConfig.xml"})
public class Config {

}
