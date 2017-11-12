package com.forgetfulman.alexa.orderbank;

import com.forgetfulman.alexa.connect.HttpClient;
import com.forgetfulman.alexa.connect.HttpMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearch {

    private static final Map<String, String> requestProperties = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(ElasticSearch.class);

    String call(String urlToCall, String requestData) {
        return new HttpClient().request(urlToCall, HttpMethod.POST, requestData, requestProperties);
    }

    public String call(String urlToCall) {
        InputStreamReader inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();

        try {
            String line;
            URL url = new URL(urlToCall);
            inputStream = new InputStreamReader(url.openStream(), Charset.forName("UTF-8"));
            bufferedReader = new BufferedReader(inputStream);
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            builder.setLength(0);
            log.error("Call to " + urlToCall + " failed!");
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(bufferedReader);
        }

        if (builder.length() == 0) {
            return "";
        } else {
            return builder.toString();
        }
    }
}
