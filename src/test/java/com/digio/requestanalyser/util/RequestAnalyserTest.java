package com.digio.requestanalyser.util;

import com.digio.requestanalyser.model.TrimmedRequestDetail;
import com.digio.requestanalyser.reader.RequestLogFileReader;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RequestAnalyserTest {

    private static final ArrayList<TrimmedRequestDetail> TEST_LIST = new ArrayList<TrimmedRequestDetail>(Arrays.asList(new TrimmedRequestDetail("123.1.1.0", "http.test1"),
            new TrimmedRequestDetail("123.2.1.0", "http.test2"),
            new TrimmedRequestDetail("123.3.1.0", "http.test3"),
            new TrimmedRequestDetail("123.3.1.0", "http.duplicate1"),
            new TrimmedRequestDetail("123.4.1.0", "http.duplicate1")));

    @InjectMocks
    private RequestAnalyser requestAnalyser;

    @Mock
    public RequestLogFileReader mockedRequestLogFileReader;

    @BeforeEach
    void setUp() {
        mockedRequestLogFileReader = new RequestLogFileReader();

    }

    @Test
    public void verifyIPAddressesAreCounted() throws URISyntaxException {
        //setup
        Mockito.when(mockedRequestLogFileReader.read(Mockito.any())).thenReturn(TEST_LIST);
        //execution
        HashMap<String, String> result = requestAnalyser.analyse("mocked.log");
        //validation
        assertEquals(result.get("unipueIPs"), "4");

    }
}
