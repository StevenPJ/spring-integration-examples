package com.sainsburys

import spock.lang.Specification

class NoIntegrationSpec extends Specification {

    def isBar = { bar -> assert bar.bar == "value" }
    def isFoo = { foo -> assert foo.foo == "value" }

    def "should transform and send data message to foo and bar"() {
        expect:
        new DataToFooBarProcessor(isFoo, isBar).process(new Data("value"))
    }
}
