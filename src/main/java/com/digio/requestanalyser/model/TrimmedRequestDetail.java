package com.digio.requestanalyser.model;

public class TrimmedRequestDetail {

    private String IP;
    private String URL;

    public TrimmedRequestDetail(String ip, String url) {
        IP = ip;
        URL = url;
    }

    public String getIP() {
        return IP;
    }

    public String getURL() {
        return URL;
    }
}
