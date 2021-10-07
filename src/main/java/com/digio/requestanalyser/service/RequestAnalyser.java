package com.digio.requestanalyser.service;

import com.digio.requestanalyser.RequestAnalyserApplication;
import com.digio.requestanalyser.model.TrimmedRequestDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Component
public class RequestAnalyser {

    private static Logger LOG = LoggerFactory
            .getLogger(RequestAnalyserApplication.class);

    public void analyse(String arg) throws URISyntaxException {
        LOG.info("Parsing log file: " + arg);

        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(arg)).toURI());
        ArrayList<TrimmedRequestDetail> trimmedRequestDetails = new ArrayList<>();

        try (Stream<String> requestDetailStream = Files.lines(path)) {
            requestDetailStream.forEach(line -> {
                String urlSubstring = line.substring(line.indexOf("\"") + 5);
                String url = urlSubstring.split(" ")[0];
                String IP = line.substring(0, line.indexOf(" "));
                trimmedRequestDetails.add(new TrimmedRequestDetail(IP, url));
            });

            LOG.info("Number of unique IP Addresses found: " +
                    getNumberOfUniqueIPAddresses(trimmedRequestDetails).toString());
            LOG.info("The top 3 most visited URLs are: " +
                    getTopKMostActiveElements(getElementArray(trimmedRequestDetails, "URL"), 3));
            LOG.info("The top 3 most active IP addresses are: " +
                    getTopKMostActiveElements(getElementArray(trimmedRequestDetails, "IP"), 3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getElementArray(ArrayList<TrimmedRequestDetail> trimmedRequestDetails, String eleType) {
        // could be switch or more sensible if statement as necessary
        return eleType.equals("URL") ?
                trimmedRequestDetails
                .stream()
                .map(TrimmedRequestDetail::getURL)
                .toArray(String[]::new):
                trimmedRequestDetails
                .stream()
                .map(TrimmedRequestDetail::getIP)
                .toArray(String[]::new);
    }

    private String getTopKMostActiveElements(String[] ele, int k) {
        if (k == ele.length) {
            return String.join(", ", ele);
        }

        Map<String, Integer> count = new HashMap();
        for (String ip: ele) {
            count.put(ip, count.getOrDefault(ip, 0) + 1);
        }

        Queue<String> heap = new PriorityQueue<>(Comparator.comparingInt(count::get));

        for (String ip: count.keySet()) {
            heap.add(ip);
            if (heap.size() > k) heap.poll();
        }

        String[] top = new String[k];
        for(int i = k - 1; i >= 0; --i) {
            top[i] = heap.poll();
        }
        return String.join(", ", top);
    }

    private Long getNumberOfUniqueIPAddresses(ArrayList<TrimmedRequestDetail> details) {
        return details.stream().map(TrimmedRequestDetail::getIP).distinct().count();
    }
}


