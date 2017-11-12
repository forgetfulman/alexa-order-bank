package com.forgetfulman.alexa.orderbankquestions;

import com.fasterxml.jackson.databind.JsonNode;
import com.forgetfulman.alexa.orderbank.ElasticSearch;
import com.forgetfulman.alexa.orderbank.GOBOrder;
import com.forgetfulman.alexa.orderbank.OrderBankQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class OrderBankAnswers implements OrderBankQuestions {

    private static final Logger log = LoggerFactory.getLogger(OrderBankAnswers.class);
    private OrderBankQueries orderBankQueries;

    public OrderBankAnswers() {
        orderBankQueries = OrderBankQueries.answeredBy(theOrderBank());
    }

    public OrderBankAnswers(ElasticSearch elasticSearchInstance) {
        orderBankQueries = OrderBankQueries.answeredBy(theOrderBank()).using(elasticSearchInstance);
    }

    @Override
    public String whatAreTheDetailsOfOrder(String orderId) {

        StringBuilder speechText = new StringBuilder();

        GOBOrder order = orderBankQueries.orderDetailsFor(orderId);
        if (order == null) {
            String msg = "Order " + orderId + " cannot be found in the Order Bank!";
            log.error(msg);
            return msg;
        }

        speechText.append("Order ").append(orderId).append(" is for a ");

        JsonNode catalogFeatureDictionary = orderBankQueries.featureDictionaryFor(order.catalog);
        String paint = orderBankQueries.descriptionFor(order.paint, catalogFeatureDictionary);
        List<String> descriptions = orderBankQueries.descriptionsFor(order.noUserChoice, catalogFeatureDictionary);
        descriptions.addAll(orderBankQueries.descriptionsFor(order.series, catalogFeatureDictionary));

        speechText.append(order.brand.equals("L") ? "Lincoln" : "Ford");
        speechText.append(" in ") .append(paint).append(" paint");
        Iterator<String> featureDescriptions = descriptions.iterator();
        int count = 0;

        while (featureDescriptions.hasNext()) {
            String feature = featureDescriptions.next();
            count++;
            if (feature.toLowerCase().contains("engine") || feature.toLowerCase().contains("transmission")) {
                if (count == 1) {
                    speechText.append(", with a ");
                }
                if (count > 1) {
                    speechText.append(" and a ");
                }
                speechText.append(feature);
            }
        }
        return speechText.toString();
    }

    @Override
    public String whatIsTheTotalNumberOfOrders() {
        return orderBankQueries.orderTotal();
    }

    @Override
    public String whatIsTheNumberOfOnHoldOrders() {
        return orderBankQueries.onHoldTotal();
    }

    @Override
    public String whenWasTheMostRecentOrderUpdate() {
        return orderBankQueries.latestOrderUpdateDate();
    }
}
