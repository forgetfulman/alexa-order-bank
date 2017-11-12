package com.forgetfulman.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.StandardCard;
import com.forgetfulman.alexa.orderbankquestions.OrderBankQuestions;
import com.google.inject.Inject;

import java.util.Optional;

public class OrderDetailsIntentAction implements IntentAction {

    private static final String SLOT_NAME = "orderId";

    private final OrderBankQuestions orderBank;

    @Inject
    protected OrderDetailsIntentAction(OrderBankQuestions orderService) {
        this.orderBank = orderService;
    }

    @Override
    public SpeechletResponse perform(final Intent intent, final Session session) {
        return Optional.ofNullable(intent.getSlot(SLOT_NAME))
                .map(Slot::getValue)
                .map(this::getOrderDetails)
                .orElse(getRepromptResposne());
    }

    private SpeechletResponse getRepromptResposne() {
        final String responseText = "Please provide an Order Number";

        StandardCard standardCard = new StandardCard();
        standardCard.setTitle("Which Order Number?");
        standardCard.setText(responseText);

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt, standardCard);
    }

    private SpeechletResponse getOrderDetails(final String orderId) {
        StandardCard standardCard = new StandardCard();

        final String responseText = orderBank.whatAreTheDetailsOfOrder(orderId);

        standardCard.setTitle("Order Details [ ID: " + orderId + " ]");
        standardCard.setText(responseText);

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        return SpeechletResponse.newTellResponse(plainTextOutputSpeech, standardCard);
    }

}
