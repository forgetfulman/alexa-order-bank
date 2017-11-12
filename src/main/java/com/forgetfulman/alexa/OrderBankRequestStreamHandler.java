package com.forgetfulman.alexa;


import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.inject.Guice;

import java.util.HashSet;
import java.util.Set;

public class OrderBankRequestStreamHandler extends SpeechletRequestStreamHandler {
    /**
     * This class is the handler for the AWS Lambda function powering the Alexa Skills Kit.
     */
    private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
        supportedApplicationIds.add("AMAZON APP ID GOES HERE");
    }

    public OrderBankRequestStreamHandler() {
        super(Guice.createInjector(new SkillStarterModule()).getInstance(OrderBankSpeechlet.class),
                supportedApplicationIds);
    }
}
