package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.SignUpSlotForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CancelSignUpSlotActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletResources resources;

    @Mock
    SlotService service;

    @Mock
    SignUpSlotForm form;

    private CancelSignUpSlotAction action;

    @Before
    public void init() {
        action = spy(new CancelSignUpSlotAction());
        action.setSlotService(service);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(User.builder().id(1L).build());
    }

    @Test
    public void executeShouldForwardToShowSignedUpSlotsAction() {

        when(resources.getForward("ShowSignedUpSlotsAction")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

    @Test
    public void cancelSigningToSlotShouldUpdateSlot(){
        action.execute(request, response, form, resources);

        verify(service).updateSlotStatus(anyLong(), anyLong(), anyBoolean());
    }

    @Test
    public void executeShouldSetSuccessfulAttribute(){

        when(service.updateSlotStatus(anyLong(), anyLong(), anyBoolean())).thenReturn(true);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "slotSearch.result.successfulCancelSignUp");
    }

    @Test
    public void executeShouldSetFailedAttribute(){

        when(service.updateSlotStatus(anyLong(), anyLong(), anyBoolean())).thenReturn(false);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "slotSearch.result.failedCancelSignUp");
    }
}