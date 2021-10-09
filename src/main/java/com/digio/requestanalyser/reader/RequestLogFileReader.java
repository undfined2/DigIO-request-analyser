package com.digio.requestanalyser.reader;

import com.digio.requestanalyser.model.TrimmedRequestDetail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class RequestLogFileReader {

    public ArrayList<TrimmedRequestDetail> read(String filename) throws URISyntaxException {

        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).toURI());
        ArrayList<TrimmedRequestDetail> trimmedRequestDetails = new ArrayList<>();
        try (
                Stream<String> requestDetailStream = Files.lines(path)) {
            requestDetailStream.forEach(line -> {
                String urlSubstring = line.substring(line.indexOf("\"") + 5);
                String url = urlSubstring.split(" ")[0];
                String IP = line.substring(0, line.indexOf(" "));
                trimmedRequestDetails.add(new TrimmedRequestDetail(IP, url));
            });
            return trimmedRequestDetails;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return trimmedRequestDetails;
    }
}
