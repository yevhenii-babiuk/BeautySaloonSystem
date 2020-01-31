package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.SlotForm;
import com.saloon.beauty.web.controllers.forms.SlotUpdateForm;
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
public class UpdateSlotActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletResources resources;

    @Mock
    SlotService service;

    private SlotUpdateForm form;
    private UpdateSlotAction action;

    private final LocalTime TEST_START_TIME = LocalTime.now().plusHours(3);
    private final LocalTime TEST_END_TIME = LocalTime.now().plusHours(5);

    @Before
    public void init() {
        action = new UpdateSlotAction();
        action.setSlotService(service);
        form = new SlotUpdateForm();
        form.setSlotId(11L);
        form.setProcedure(1L);
        form.setDate(LocalDate.now());
        form.setStartTime(TEST_START_TIME);
        form.setEndTime(TEST_END_TIME);
        form.setMasterId(1L);
    }

    @Test
    public void executeShouldUpdateSlot(){
        action.execute(request, response, form, resources);

        verify(service).updateSlot(11L, 1L, 1L, LocalDate.now(), TEST_START_TIME, TEST_END_TIME);
    }

    @Test
    public void executeShouldSetSuccessfulAttribute(){

        when(service.updateSlot(anyLong(), anyLong(), anyLong(), any(LocalDate.class), any(LocalTime.class),  any(LocalTime.class))).thenReturn(true);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "slotManagement.updateSlot.updatingSuccessful");
    }

    @Test
    public void executeShouldSetFailedAttribute(){

        when(service.updateSlot(anyLong(), anyLong(), anyLong(), any(LocalDate.class), any(LocalTime.class),  any(LocalTime.class))).thenReturn(false);
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "slotManagement.updateSlot.updatingFailed");
    }

    @Test
    public void executeShouldForwardToShowSlotSearchAdminPage() {
        when(resources.getForward("ShowSlotSearchAdminPage")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

}