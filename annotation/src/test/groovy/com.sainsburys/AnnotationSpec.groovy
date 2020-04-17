package com.sainsburys

import com.sainsburys.beans.Config
import com.sainsburys.beans.Data
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.channel.QueueChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [Config])
class AnnotationSpec extends Specification {

    @Autowired
    MessageChannel json_to_data

    @Autowired
    QueueChannel bar_out

    @Autowired
    QueueChannel foo_out


    def "should transform and send data message to foo and bar"() {
        when:
        json_to_data.send(MessageBuilder.withPayload(JsonOutput.toJson(new Data("value"))).build())
        then:
        bar_out.receive(0).payload == JsonOutput.toJson([bar: "value"])
        foo_out.receive(0).payload == JsonOutput.toJson([foo: "value"])
    }
}
