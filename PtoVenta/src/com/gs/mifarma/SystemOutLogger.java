package com.gs.mifarma;

import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Redecciona System out
 * @author ERIOS
 * @since 2.2.8
 */
public class SystemOutLogger {

    private static final Logger log = LoggerFactory.getLogger(SystemOutLogger.class);

    public SystemOutLogger() {
        super();
    }

    public static void main(String[] args) {
        redirect();

        log.trace("Hello World!");
        log.debug("How are you today?");
        log.info("I am fine.");
        log.warn("I love programming.");
        log.error("I am programming.");

        try {
            int cal = 100 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Fin");
    }

    /**
     * Redirects stdout and stderr to logger
     */
    public static void redirect() {
        System.setOut(new PrintStream(System.out) {
            public void print(String s) {
                log.debug(s);
            }
        });
        System.setErr(new PrintStream(System.err) {
            public void print(String s) {
                log.warn(s);
            }
        });
    }
}
