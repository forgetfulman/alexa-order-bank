package com.forgetfulman.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class AmazonStopIntentAction implements IntentAction {

    protected AmazonStopIntentAction() {
    }

    @Override
    public SpeechletResponse perform(Intent intent, Session session) {

        String speechText = "Order bank request cancelled";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Order Bank Request Cancelled");
        card.setContent("Request cancelled");

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

}
