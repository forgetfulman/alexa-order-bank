package com.forgetfulman.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class AmazonHelpIntentAction implements IntentAction {

    protected AmazonHelpIntentAction() {
    }

    @Override
    public SpeechletResponse perform(Intent intent, Session session) {
        String speechText =
                "You can request the following services" +
                ", total order count" +
                ", total orders on gatekeeper hold" +
                ", the date the order bank was last updated" +
                ", or details of a specific order.";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Order Bank Help");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}
