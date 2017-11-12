package com.forgetfulman.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.StandardCard;
import com.forgetfulman.alexa.orderbankquestions.OrderBankQuestions;

import javax.inject.Inject;

public class LatestOrderTimestampIntentAction implements IntentAction {

    private final OrderBankQuestions orderService;
    private final String dateOpeningTag = "<say-as interpret-as='date'>";
    private final String dateClosingTag = "</say-as>";

    @Inject
    protected LatestOrderTimestampIntentAction(OrderBankQuestions orderService) {
        this.orderService = orderService;
    }

    @Override
    public SpeechletResponse perform(Intent intent, Session session) {

        String speechText = "The latest order bank update was made on ";

        StandardCard standardCard = new StandardCard();

        String orderTimestamp = orderService.whenWasTheMostRecentOrderUpdate();
        speechText = orderTimestamp.isEmpty() ? "No orders were found in the order bank" :
                (speechText += dateOpeningTag + orderTimestamp + dateClosingTag);

        standardCard.setTitle("Latest Order Bank Update");
        standardCard.setText(speechText);

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(speechText);

        return SpeechletResponse.newTellResponse(plainTextOutputSpeech, standardCard);
    }
}
