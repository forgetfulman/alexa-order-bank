package com.forgetfulman.alexa.orderbank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import static com.forgetfulman.alexa.orderbank.OrderBankResponse.extractFrom;

public class OrderBankQueries {

    private static final Logger log = LoggerFactory.getLogger(OrderBankQueries.class);

    private static String orderIndex;
    private static String featureIndex;
    private static String elasticSearchUrl;
    private ElasticSearch elasticSearch = new ElasticSearch();

    public static OrderBankQueries answeredBy(Properties theOrderBank) {
        elasticSearchUrl = theOrderBank.getProperty("elasticSearchUrl");
        orderIndex = theOrderBank.getProperty("orderIndex");
        featureIndex = theOrderBank.getProperty("featureIndex");
        return new OrderBankQueries();
    }

    public OrderBankQueries using(ElasticSearch elasticSearchInstance) {
        elasticSearch = elasticSearchInstance;
        return this;
    }

    public String orderTotal() {
        String esResponse = elasticSearch.call(elasticSearchUrl + orderIndex + "/_stats/docs");
        JsonNode indexSummary = extractFrom(esResponse, "indices", orderIndex);
        String orderTotal = indexSummary.at("/primaries/docs/count").asText();

        return orderTotal;
    }

    public String onHoldTotal() {
        String requestData = "{ \"size\" : 0, \"query\" : { \"term\" : { \"holdState\" : \"N\" } } }";
        String esResponse = elasticSearch.call(elasticSearchUrl + orderIndex + "/_search", requestData);
        JsonNode ordersOnHoldQueryResult = extractFrom(esResponse, "hits", "total");
        String onHoldTotal = ordersOnHoldQueryResult.asText();

        return onHoldTotal;
    }

    public String latestOrderUpdateDate() {
        String requestData = "{ \"query\": { \"match_all\": {} }, \"size\": 1, \"sort\": { \"GTDV01_LAST_UPDT_S\": { \"order\": \"desc\" } } }";
        String esResponse = elasticSearch.call(elasticSearchUrl + orderIndex + "/_search", requestData);
        SearchResponse processedResponse;

        try {
            processedResponse = new ObjectMapper().readValue(esResponse, SearchResponse.class);
        } catch (final Exception e) {
            log.error("Unable to parse Last Updated order extractFrom ElasticSearch!");
            throw new RuntimeException(e);
        }

        String updateDate;
        GOBOrder lastUpdatedOrder;

        if (processedResponse.result.orders.size() > 0) {
            lastUpdatedOrder = processedResponse.result.orders.get(0).order;
        } else {
            log.warn("No orders found in the order bank!");
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        updateDate = sdf.format(lastUpdatedOrder.GTDV01_LAST_UPDT_S);

        return updateDate;
    }

    public JsonNode featureDictionaryFor(String catalog) {
        String esI18nResponse = elasticSearch.call(elasticSearchUrl + featureIndex + "/_search?q=_id:" + catalog);
        return extractFrom(esI18nResponse, "hits", "hits");
    }

    public List<String> descriptionsFor(List<String> features, JsonNode catalogFeatureDictionary) {
        List<String> featureDescriptions = new ArrayList<>();
        for (String feature : features) {
            featureDescriptions.add(descriptionFor(feature, catalogFeatureDictionary));
        }
        return featureDescriptions;
    }

    public String descriptionFor(String feature, JsonNode featureNode) {
        String translatedCode = "feature description unavailable";
        if (!(featureNode == null) && !featureNode.isMissingNode()) {
            for (JsonNode i18nNode : featureNode) {
                JsonNode arrayNode = i18nNode.path("_source");
                translatedCode = arrayNode.path(feature).asText();
            }
        }
        return translatedCode;
    }

    public GOBOrder orderDetailsFor(String orderId) {
        String esResponse = elasticSearch.call( elasticSearchUrl + orderIndex + "/_search?q=orderId:" + orderId);
        SearchResponse processedResponse;
        GOBOrder order;

        try {
            processedResponse = new ObjectMapper().readValue(esResponse, SearchResponse.class);
        } catch (final Exception e) {
            log.error("Unable to parse response for order " + orderId + " extractFrom Elasticsearch!");
            return null;
        }

        if (processedResponse.result.orders.size() > 0) {
            order = processedResponse.result.orders.get(0).order;
        } else {
            log.error("Could not find order " + orderId + " in the order bank!");
            return null;
        }

        return order;
    }

}
