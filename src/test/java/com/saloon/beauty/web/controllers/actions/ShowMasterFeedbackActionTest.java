package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.dto.SlotDto;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowMasterFeedbackActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletResources resources;

    @Mock
    SlotService slotService;

    @Mock
    PaginationHelper helper;

    @Mock
    ActionForm form;

    private ShowMasterFeedbackAction action;

    @Before
    public void init() {
        action = spy(new ShowMasterFeedbackAction());
        action.setSlotService(slotService);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void executeShouldForwardToShowMasterFeedbackPage(){
        when(session.getAttribute(anyString())).thenReturn(User.builder().id(1L).build());

        when(resources.getForward("ShowMasterFeedbackPage")).thenReturn("testPath");

        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

    @Test
    public void executeShouldProperlyCallLocalMethods(){
        SlotDto mockSlot = mock(SlotDto.class, RETURNS_DEEP_STUBS);
        when(mockSlot.getFeedback().getText()).thenReturn("test text");
        List<SlotDto> list = Collections.singletonList(mockSlot);

        doReturn(list).when(action).getMasterSlots(eq(1L), eq(request), eq(slotService), any(PaginationHelper.class));
        doNothing().when(action).addPaginationToRequest(anyLong(), eq(request), any(PaginationHelper.class));
        when(session.getAttribute(anyString())).thenReturn(User.builder().id(1L).build());

        action.execute(request, response, form, resources);

        verify(action).getMasterSlots(eq(1L), eq(request), eq(slotService), any(PaginationHelper.class));
        verify(action).addPaginationToRequest(eq(1L), eq(request), any(PaginationHelper.class));
    }

    @Test
    public void addPaginationToRequestShouldProperlyAddPagination() {
        when(slotService.getSlotSearchResultCount(anyLong(), eq(null), anyLong(), anyLong(), eq(null),
                eq(null), eq(null), eq(null), eq(true))).thenReturn(37L);
        action.addPaginationToRequest(3L , request, helper);
        verify(helper).addPaginationToRequest(request, 37L);
    }
}