package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.AddProcedureForm;
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
public class AddProcedureActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletResources resources;

    @Mock
    ProcedureService service;

    private AddProcedureForm form;
    private AddProcedureAction action;

    @Before
    public void init() {
        action = new AddProcedureAction();
        form = new AddProcedureForm();
        action.setProcedureService(service);
        form.setNameEnglish("NameEn");
        form.setDescriptionEnglish("DescriptionEn");
        form.setNameUkrainian("NameUa");
        form.setDescriptionUkrainian("DescriptionUa");
        form.setNameRussian("NameRu");
        form.setDescriptionRussian("DescriptionRu");
        form.setPrice(333);
    }

    @Test
    public void executeShouldSaveProcedureAndReturnPositiveResult(){
        action.execute(request, response, form, resources);

        verify(service).addNewProcedure("NameUa", "DescriptionUa",
                "NameEn", "DescriptionEn",
                "NameRu", "DescriptionRu", 333);
    }

    @Test
    public void executeShouldSetSuccessfulAttribute(){

        when(service.addNewProcedure(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(7L);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "procedureManagement.addProcedure.addingSuccessful");
    }

    @Test
    public void executeShouldSetFailedAttribute(){

        when(service.addNewProcedure(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(0L);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "procedureManagement.addProcedure.addingFailed");
    }

    @Test
    public void executeShouldForwardToShowSignedUpSlotsAction() {
        when(resources.getForward("ShowProceduresAction")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

}