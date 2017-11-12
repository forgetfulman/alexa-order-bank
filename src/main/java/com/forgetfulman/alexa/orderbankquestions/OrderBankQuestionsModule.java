package com.forgetfulman.alexa.orderbankquestions;

import com.google.inject.AbstractModule;

public class OrderBankQuestionsModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(OrderBankQuestions.class).to(OrderBankAnswers.class);
        }

}
