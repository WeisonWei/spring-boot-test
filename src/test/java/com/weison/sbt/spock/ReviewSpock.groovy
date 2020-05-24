package com.weison.sbt.spock

import spock.lang.Specification

class ReviewSpock extends Specification {

    def "test"() {
        given:

        def namelist = new ArrayList()
        def elem = "Weison"

        when:
        namelist.add(elem)

        then:
        !namelist.empty
        namelist.size() == 2
        namelist.get(0).equals("Weison")
        noExceptionThrown()
    }

    def "test exception"() {
        given:

        def namelist = new ArrayList()
        def elem = "Weison"

        when:
        namelist.add(elem)
        namelist.get(2)

        then:
        thrown(IndexOutOfBoundsException.class)
    }
}
