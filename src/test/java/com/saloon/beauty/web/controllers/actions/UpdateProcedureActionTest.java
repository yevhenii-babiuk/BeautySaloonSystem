package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.AddProcedureForm;
import com.saloon.beauty.web.controllers.forms.UpdateProcedureForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateProcedureActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletResources resources;

    @Mock
    ProcedureService service;

    private UpdateProcedureForm form;
    private UpdateProcedureAction action;

    @Before
    public void init() {
        action = new UpdateProcedureAction();
        form = new UpdateProcedureForm();
        action.setProcedureService(service);
        form.setProcedureId(6L);
        form.setNameEnglish("NameEn");
        form.setDescriptionEnglish("DescriptionEn");
        form.setNameUkrainian("NameUa");
        form.setDescriptionUkrainian("DescriptionUa");
        form.setNameRussian("NameRu");
        form.setDescriptionRussian("DescriptionRu");
        form.setPrice(333);
    }

    @Test
    public void executeShouldSetSuccessfulAttribute(){
        when(service.updateProcedure(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(true);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "procedureManagement.updateProcedure.updatingSuccessful");
    }

    @Test
    public void executeShouldSetFailedAttribute(){
        when(service.updateProcedure(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(false);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "procedureManagement.updateProcedure.updatingFailed");
    }

    @Test
    public void executeShouldForwardToShowProceduresAction() {
        when(resources.getForward("ShowProceduresAction")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }
}