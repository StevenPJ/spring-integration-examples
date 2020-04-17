package com.sainsburys

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ImportResource

@TestConfiguration
@ImportResource(locations = ["classpath:/config.xml"])
class TestConfig {}
