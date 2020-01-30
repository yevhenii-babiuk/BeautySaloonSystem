package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.SlotForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddSlotActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletResources resources;

    @Mock
    SlotService service;

    private SlotForm form;
    private AddSlotAction action;

    private final LocalTime TEST_START_TIME = LocalTime.now().plusHours(3);
    private final LocalTime TEST_END_TIME = LocalTime.now().plusHours(5);

    @Before
    public void init() {
        action = new AddSlotAction();
        action.setSlotService(service);
        form = new SlotForm();
        form.setProcedure(1L);
        form.setDate(LocalDate.now());
        form.setStartTime(TEST_START_TIME);
        form.setEndTime(TEST_END_TIME);
        form.setMasterId(1L);
    }

    @Test
    public void executeShouldSaveSlotAndReturnPositiveResult(){
        action.execute(request, response, form, resources);

        verify(service).addNewSlot(LocalDate.now(), TEST_START_TIME, TEST_END_TIME, 1L, 0L, 1L  );
    }

    @Test
    public void executeShouldSetSuccessfulAttribute(){

        when(service.addNewSlot(any(), any(), any(), anyLong(), anyLong(), anyLong())).thenReturn(7L);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "slotManagement.addSlot.addingSuccessful");
    }

    @Test
    public void executeShouldSetFailedAttribute(){

        when(service.addNewSlot(any(), any(), any(), anyLong(), anyLong(), anyLong())).thenReturn(0L);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "slotManagement.addSlot.addingFailed");
    }



}