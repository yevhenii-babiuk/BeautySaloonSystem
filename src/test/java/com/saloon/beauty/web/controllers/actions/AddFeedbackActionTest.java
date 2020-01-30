package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.FeedbackService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.AddFeedbackForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddFeedbackActionTest {


    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletResources resources;

    @Mock
    FeedbackService feedbackService;

    private AddFeedbackForm form;
    private AddFeedbackAction action;

    @Before
    public void init() {
        action = new AddFeedbackAction();
        form = new AddFeedbackForm();
        action.setFeedbackService(feedbackService);
    }

    @Test
    public void executeShouldSaveFeedback(){
        form.setFeedbackText("any text");
        form.setSlotId(1L);
        action.execute(request, response, form, resources);
        verify(feedbackService).addFeedbackToSlot(1L, "any text");
    }

    @Test
    public void executeShouldSetFailedAttribute(){
        form.setFeedbackText("text feedback");
        form.setSlotId(8L);
        when(feedbackService.addFeedbackToSlot(anyLong(), anyString())).thenReturn(0L);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "feedbackManagement.addFeedback.slotNotEnded");
    }

    @Test
    public void executeShouldSetSuccessfulAttribute(){
        form.setFeedbackText("text feedback");
        form.setSlotId(8L);
        when(feedbackService.addFeedbackToSlot(anyLong(), anyString())).thenReturn(3L);
        action.execute(request, response, form, resources);
        verify(feedbackService).addFeedbackToSlot(8L, "text feedback");
        verify(request).setAttribute("actionResult", "feedbackManagement.addFeedback.addingSuccessful");
    }

    @Test
    public void executeShouldForwardToShowSignedUpSlotsAction() {
        when(resources.getForward("ShowSignedUpSlotsAction")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

}