package com.digio.requestanalyser;

import com.digio.requestanalyser.service.RequestAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class RequestAnalyserApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory
            .getLogger(RequestAnalyserApplication.class);

    private final RequestAnalyser requestAnalyser;

    @Autowired
    public RequestAnalyserApplication(RequestAnalyser requestAnalyser) {
        this.requestAnalyser = requestAnalyser;
    }

    public static void main(String[] args) {
        SpringApplication.run(RequestAnalyserApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Starting Request Analyser...");

        if (args.length == 0) {
            throw new InvalidParameterException("Please supply at least one log file to analyse");
        }
        for (String arg : args) {
            Pattern logFileValidation = Pattern.compile(".*\\.log");
            if (logFileValidation.matcher(arg).matches()) {
                requestAnalyser.analyse(arg);
            } else {
                LOG.error("Parameter must be a log file! Skipping " + arg);
            }
            ;


        }


    }
}
