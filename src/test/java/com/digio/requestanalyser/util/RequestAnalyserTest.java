package com.digio.requestanalyser.util;

import com.digio.requestanalyser.model.TrimmedRequestDetail;
import com.digio.requestanalyser.reader.RequestLogFileReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.digio.requestanalyser.constants.constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RequestAnalyserTest {

    private static final ArrayList<TrimmedRequestDetail> TEST_LIST = new ArrayList<TrimmedRequestDetail>(Arrays.asList(new TrimmedRequestDetail("123.1.1.0", "http.test1"),
            new TrimmedRequestDetail("123.1.1.0", "http.test2"),
            new TrimmedRequestDetail("123.1.1.0", "http.test3"),
            new TrimmedRequestDetail("123.1.1.0", "http.duplicate1"),
            new TrimmedRequestDetail("123.2.1.0", "http.duplicate1"),
            new TrimmedRequestDetail("123.2.1.0", "http.duplicate2"),
            new TrimmedRequestDetail("123.2.1.0", "http.duplicate2"),
            new TrimmedRequestDetail("123.3.1.0", "http.duplicate2"),
            new TrimmedRequestDetail("123.3.1.0", "http.duplicate3"),
            new TrimmedRequestDetail("123.4.1.0", "http.duplicate3"),
            new TrimmedRequestDetail("123.5.1.0", "http.duplicate3"),
            new TrimmedRequestDetail("123.6.1.0", "http.duplicate3"),
            new TrimmedRequestDetail("123.7.1.0", "http.duplicate3")));

    @InjectMocks
    private RequestAnalyser requestAnalyser;

    @Mock
    public RequestLogFileReader mockedRequestLogFileReader;

    @Test
    public void verifyIPAddressesAreCounted() throws URISyntaxException {
        Mockito.when(mockedRequestLogFileReader.read(Mockito.any())).thenReturn(TEST_LIST);
        HashMap<String, String> result = requestAnalyser.analyse("mocked.log");
        assertEquals(result.get(UNIQUE_IPS), "7");
    }

    @Test
    public void verifyTopThreeIPsAreReturned() throws URISyntaxException {
        Mockito.when(mockedRequestLogFileReader.read(Mockito.any())).thenReturn(TEST_LIST);
        HashMap<String, String> result = requestAnalyser.analyse("mocked.log");
        assertEquals(result.get(TOP_3_IPS), "123.1.1.0, 123.2.1.0, 123.3.1.0");
    }

    @Test
    public void verifyTopThreeURLsAreReturned() throws URISyntaxException {
        Mockito.when(mockedRequestLogFileReader.read(Mockito.any())).thenReturn(TEST_LIST);
        HashMap<String, String> result = requestAnalyser.analyse("mocked.log");
        assertEquals(result.get(TOP_3_URLS), "http.duplicate3, http.duplicate2, http.duplicate1");
    }

}
