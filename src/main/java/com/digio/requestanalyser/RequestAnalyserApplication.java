package com.digio.requestanalyser;

import com.digio.requestanalyser.service.RequestAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

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
    public void run(String... args) throws Exception, IOException {
        LOG.info("Attempting to analyse file");
        requestAnalyser.analyse(args[0]);
    }
}
