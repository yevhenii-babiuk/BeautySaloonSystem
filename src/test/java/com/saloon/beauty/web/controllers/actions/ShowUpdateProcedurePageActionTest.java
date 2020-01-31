package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.Procedure;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ProcedureIdForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowUpdateProcedurePageActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletResources resources;

    @Mock
    ProcedureService service;

    @Mock
    ProcedureIdForm form;

    private ShowUpdateProcedurePageAction action;

    @Before
    public void init() {
        action = spy(new ShowUpdateProcedurePageAction());
        action.setProcedureService(service);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void executeShouldForwardToShowProcedureUpdatePageOnSusccessful(){
        when(resources.getForward("ShowProcedureUpdatePage")).thenReturn("testPath");
        when(action.getProcedureById(form)).thenReturn(Optional.of(Procedure.builder().build()));

        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

    @Test
    public void executeShouldForwardToShowProcedureUpdatePageOnFailed(){
        when(resources.getForward("ShowProceduresAction")).thenReturn("testPath");
        when(action.getProcedureById(form)).thenReturn(Optional.empty());

        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

}