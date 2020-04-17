package com.sainsburys

import com.sainsburys.TestConfig
import com.sainsburys.beans.Data
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.integration.channel.QueueChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import spock.lang.Specification

@SpringBootTest
@Import(TestConfig)
class LeakySpec extends Specification {

    @Autowired
    MessageChannel json_to_data

    @Autowired
    QueueChannel test_bar_out

    @Autowired
    QueueChannel test_foo_out

    def "should transform and send data message to foo and bar"() {
        when:
        json_to_data.send(MessageBuilder.withPayload(JsonOutput.toJson(new Data("value"))).build())
        then:
        test_bar_out.receive(0).payload == JsonOutput.toJson([bar: "value"])
        test_foo_out.receive(0).payload == JsonOutput.toJson([foo: "value"])

    }
}
