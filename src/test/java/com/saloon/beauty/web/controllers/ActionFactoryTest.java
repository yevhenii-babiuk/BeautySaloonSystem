package com.saloon.beauty.web.controllers;

import com.saloon.beauty.web.controllers.actions.Action;
import com.saloon.beauty.web.controllers.actions.ChangeLanguageAction;
import com.saloon.beauty.web.controllers.config.ServletConfigException;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActionFactoryTest {

    @Test
    public void createActionShouldReturnCorrectAction() {
        ActionFactory factory = ActionFactory.getInstance();
        Action returnedAction = factory.createAction("com.library.web.controllers.actions.ChangeLanguageAction", false, null, Collections.emptyList());
        assertTrue(returnedAction instanceof ChangeLanguageAction);
    }

    @Test(expected = ServletConfigException.class)
    public void createActionShouldThrowException() {
        ActionFactory.getInstance().createAction("false class name", false, null, Collections.emptyList());
    }

    @Test
    public void createSetterNameShouldReturnExpectedName(){
        ActionFactory factory = ActionFactory.getInstance();
        String variableName = "test";
        String expectedSetterName = "setTest";
        String resultName = factory.createSetterName(variableName);
        assertEquals(expectedSetterName, resultName);
    }

}