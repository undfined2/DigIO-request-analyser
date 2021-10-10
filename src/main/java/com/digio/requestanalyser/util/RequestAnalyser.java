package com.digio.requestanalyser.util;

import com.digio.requestanalyser.RequestAnalyserApplication;
import com.digio.requestanalyser.model.TrimmedRequestDetail;
import com.digio.requestanalyser.reader.RequestLogFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.*;

@Component
public class RequestAnalyser {


    public RequestAnalyser(RequestLogFileReader fileReader) {
        this.fileReader = fileReader;
    }

    private final RequestLogFileReader fileReader;

    private static final Logger LOG = LoggerFactory
            .getLogger(RequestAnalyserApplication.class);

    public HashMap<String, String> analyse(String arg) throws URISyntaxException {
        LOG.info("Parsing log file: " + arg);


        ArrayList<TrimmedRequestDetail> trimmedRequestDetails = fileReader.read(arg);
        HashMap<String, String> answerMap = new HashMap<>();
        if (trimmedRequestDetails != null) {
            answerMap.put("unipueIPs", getNumberOfUniqueIPAddresses(trimmedRequestDetails).toString());
            answerMap.put("top3URLs", getTopKMostActiveElements(getElementArray(trimmedRequestDetails, "URL"), 3));
            answerMap.put("top3IPs", getTopKMostActiveElements(getElementArray(trimmedRequestDetails, "IP"), 3));
        } else {
            LOG.error("Error parsing log file: " + arg);
        }
        return answerMap;
    }

    private String[] getElementArray(ArrayList<TrimmedRequestDetail> trimmedRequestDetails, String eleType) {
        // could be switch or more sensible if statement as necessary
        return eleType.equals("URL") ?
                trimmedRequestDetails
                        .stream()
                        .map(TrimmedRequestDetail::getURL)
                        .toArray(String[]::new) :
                trimmedRequestDetails
                        .stream()
                        .map(TrimmedRequestDetail::getIP)
                        .toArray(String[]::new);
    }

    private String getTopKMostActiveElements(String[] ele, int k) {
        if (k == ele.length) {
            return String.join(", ", ele);
        }

        Map<String, Integer> count = new HashMap<>();
        for (String ip : ele) {
            count.put(ip, count.getOrDefault(ip, 0) + 1);
        }

        Queue<String> heap = new PriorityQueue<>(Comparator.comparingInt(count::get));

        for (String ip : count.keySet()) {
            heap.add(ip);
            if (heap.size() > k) heap.poll();
        }

        String[] top = new String[k];
        for (int i = k - 1; i >= 0; --i) {
            top[i] = heap.poll();
        }
        return String.join(", ", top);
    }

    private Long getNumberOfUniqueIPAddresses(ArrayList<TrimmedRequestDetail> details) {
        return details.stream().map(TrimmedRequestDetail::getIP).distinct().count();
    }
}


