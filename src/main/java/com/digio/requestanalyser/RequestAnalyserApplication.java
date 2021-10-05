package com.digio.requestanalyser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestAnalyserApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(RequestAnalyserApplication.class);

    public static void main(String[] args) {
        LOG.info("Attempting to process log file"); //todo: add arg to allow log file selection and add interpolation
        SpringApplication.run(RequestAnalyserApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");

        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
    }
}
