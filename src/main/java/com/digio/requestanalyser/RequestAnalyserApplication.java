package com.digio.requestanalyser;

import com.digio.requestanalyser.util.RequestAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.digio.requestanalyser.constants.constants.*;

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
                HashMap<String, String> result = requestAnalyser.analyse(arg);
                LOG.info("Number of unique IP Addresses found: " + result.get(UNIQUE_IPS));
                LOG.info("The top 3 most visited URLs are: " + result.get(TOP_3_URLS));
                LOG.info("The top 3 most active IP addresses are: " + result.get(TOP_3_IPS));
            } else {
                LOG.error("Parameter must be a log file! Skipping " + arg);
            }


        }
    }
}
