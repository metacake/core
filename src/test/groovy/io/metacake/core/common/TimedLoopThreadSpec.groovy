package io.metacake.core.common

import spock.lang.Specification

class TimedLoopThreadSpec extends Specification {

    def "Requesting a shutdown blocks"() {
        setup:
        TimedLoopThread thread = new TimedLoopThread({
            (0..<100).each {
                Math.cos(Math.random());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        when:
        thread.start();
        Thread.sleep(5);
        thread.requestStop();

        then:
        !thread.isAlive()
    }
}