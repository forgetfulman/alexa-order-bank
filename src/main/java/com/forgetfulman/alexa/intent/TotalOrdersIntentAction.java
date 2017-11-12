package com.forgetfulman.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.StandardCard;
import com.forgetfulman.alexa.orderbankquestions.OrderBankQuestions;

import javax.inject.Inject;

public class TotalOrdersIntentAction implements IntentAction {

    private final OrderBankQuestions orderBankQuery;

    @Inject
    protected TotalOrdersIntentAction(OrderBankQuestions orderService) {
        this.orderBankQuery = orderService;
    }

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        return getTotalOrdersResponse();
    }

    private SpeechletResponse getTotalOrdersResponse() {

        String speechText = "The total number of orders in the order bank is ";

        StandardCard standardCard = new StandardCard();

        speechText += orderBankQuery.whatIsTheTotalNumberOfOrders();

        standardCard.setTitle("Total Orders");
        standardCard.setText(speechText);

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(speechText);

        return SpeechletResponse.newTellResponse(plainTextOutputSpeech, standardCard);
    }

}
