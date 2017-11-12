package com.forgetfulman.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.StandardCard;
import com.forgetfulman.alexa.orderbankquestions.OrderBankQuestions;

import javax.inject.Inject;

public class TotalOrdersOnHoldIntentAction implements IntentAction {

    private final OrderBankQuestions orderService;

    @Inject
    protected TotalOrdersOnHoldIntentAction(OrderBankQuestions orderService) {
        this.orderService = orderService;
    }

    @Override
    public SpeechletResponse perform(Intent intent, Session session) {

        String speechText = "The number of orders on gatekeeper hold is ";

        StandardCard standardCard = new StandardCard();

        speechText += orderService.whatIsTheNumberOfOnHoldOrders();

        standardCard.setTitle("Total Orders on Gatekeeper Hold");
        standardCard.setText(speechText);

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(speechText);

        return SpeechletResponse.newTellResponse(plainTextOutputSpeech, standardCard);
    }
}
