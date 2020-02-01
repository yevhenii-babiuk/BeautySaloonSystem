package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.web.controllers.PaginationHelper;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShowSignedUpSlotsActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletResources resources;

    @Mock
    PaginationHelper helper;

    @Mock
    SlotService service;

    @Mock
    ActionForm form;

    private ShowSignedUpSlotsAction action;

    @Before
    public void init() {
        action = spy(new ShowSignedUpSlotsAction());
        action.setSlotService(service);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void executeShouldForwardToShowSignedUpSlotsPage(){
        when(session.getAttribute(anyString())).thenReturn(User.builder().id(1L).build());
        when(resources.getForward("ShowSignedUpSlotsPage")).thenReturn("testPath");

        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

    @Test
    public void executeShouldProperlyCallLocalMethods(){
        doReturn(Collections.emptyList()).when(action).getUserSlots(eq(1L), eq(request), eq(service),  any(PaginationHelper.class));
        doNothing().when(action).addPaginationToRequest(eq(1L), eq(request), any(PaginationHelper.class));
        when(session.getAttribute(anyString())).thenReturn(User.builder().id(1L).build());

        action.execute(request, response, form, resources);

        verify(action).getUserSlots(eq(1L), eq(request), eq(service),  any(PaginationHelper.class));
        verify(action).addPaginationToRequest(eq(1L), eq(request), any(PaginationHelper.class));
    }

    @Test
    public void addPaginationToRequestShouldProperlyAddPagination() {
        when(service.getSlotSearchResultCount(anyLong(), eq(Status.BOOKED), anyLong(), anyLong(), eq(null),
                eq(null), eq(null), eq(null), eq(false))).thenReturn(37L);
        action.addPaginationToRequest(3L , request, helper);
        verify(helper).addPaginationToRequest(request, 37L);
    }
}