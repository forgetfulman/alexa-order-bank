package com.forgetfulman.alexa.orderbankquestions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface OrderBankQuestions {

    String whatAreTheDetailsOfOrder(String orderId);

    String whatIsTheTotalNumberOfOrders();

    String whatIsTheNumberOfOnHoldOrders();

    String whenWasTheMostRecentOrderUpdate();

    default Properties theOrderBank() {
        Properties serviceProperties = new Properties();
        Logger log = LoggerFactory.getLogger(OrderBankQuestions.class);
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("order-bank.properties");
            serviceProperties.load(stream);
        } catch (IOException ioe) {
            log.error("Unable to load order-bank properties file!");
        }
        return serviceProperties;
    }
}
