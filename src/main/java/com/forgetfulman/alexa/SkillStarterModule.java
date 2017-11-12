package com.forgetfulman.alexa;

import com.forgetfulman.alexa.intent.IntentModule;
import com.forgetfulman.alexa.orderbankquestions.OrderBankQuestionsModule;
import com.google.inject.AbstractModule;

/*
 * A parent module to install the all modules required by the application.
 */
public class SkillStarterModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new OrderBankQuestionsModule());
        install(new IntentModule());
    }

}
