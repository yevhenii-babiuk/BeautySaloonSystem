package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.ProcedureService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowProcedurePageActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletResources resources;

    @Mock
    PaginationHelper helper;

    @Mock
    ProcedureService service;

    @Mock
    ActionForm form;

    private ShowProcedurePageAction action;

    @Before
    public void init() {
        action = spy(new ShowProcedurePageAction());
        action.setProcedureService(service);
    }

    @Test
    public void executeShouldForwardToShowProceduresPage(){
        when(resources.getForward("ShowProceduresPage")).thenReturn("testPath");

        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

    @Test
    public void executeShouldProperlyCallLocalMethods(){
        doReturn(Collections.emptyList()).when(action).getProceduresList(eq(request), eq(service),  any(PaginationHelper.class));
        doNothing().when(action).setRequestAttributes(eq(request), eq(Collections.emptyList()));
        doNothing().when(action).addPaginationToRequest(eq(request), eq(service),  any(PaginationHelper.class));

        action.execute(request, response, form, resources);

        verify(action).addPaginationToRequest(eq(request), eq(service),  any(PaginationHelper.class));
        verify(action).setRequestAttributes(eq(request), eq(Collections.emptyList()));
        verify(action).getProceduresList(eq(request), eq(service),  any(PaginationHelper.class));
    }

    @Test
    public void addPaginationToRequestShouldProperlyAddPagination() {
        when(service.getProcedureSearchResultCount()).thenReturn(37L);
        action.addPaginationToRequest( request, service, helper);
        verify(helper).addPaginationToRequest(request, 37L);
    }
}