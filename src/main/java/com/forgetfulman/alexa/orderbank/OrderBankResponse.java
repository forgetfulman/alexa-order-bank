package com.forgetfulman.alexa.orderbank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class OrderBankResponse {

    private static final Logger log = LoggerFactory.getLogger(OrderBankResponse.class);

    public static JsonNode extractFrom(String json, String dataCategory, String dataName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(json);
            JsonNode indexNodes = rootNode.path(dataCategory);
            Iterator<Map.Entry<String, JsonNode>> categories = indexNodes.fields();
            while (categories.hasNext()) {
                Map.Entry<String, JsonNode> entry = categories.next();
                if (entry.getKey().equals(dataName)) return entry.getValue();
            }
        }
        catch (IOException ioe) {
            log.error("Cannot locate data item " + dataName + " extractFrom category " + dataCategory +" in ElasticSearch response!");
        }
        return null;
    }
}

