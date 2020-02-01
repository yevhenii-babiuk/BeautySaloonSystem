package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.PaginationHelper;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.SlotSearchForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SlotSearchActionTest {

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
    UserService userService;

    @Mock
    ProcedureService procedureService;

    @Mock
    PaginationHelper helper;

    private SlotSearchAction action;
    private SlotSearchForm form;

    private final long TEST_MASTER_ID = 7L;
    private final long TEST_PROCEDURE_ID = 3L;
    private final LocalDate TEST_MIN_DATE = LocalDate.now().minusDays(3);
    private final LocalDate TEST_MAX_DATE = LocalDate.now().plusDays(3);
    private final LocalTime TEST_MIN_TIME = LocalTime.of(10,30);
    private final LocalTime TEST_MAX_TIME = LocalTime.of(14,50);

    @Before
    public void init() {
        action = spy(new SlotSearchAction());
        form = new SlotSearchForm();
        action.setProcedureService(procedureService);
        action.setSlotService(slotService);
        action.setUserService(userService);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void executeShouldForwardToShowSlotSearchPage() {
        when(userService.getAllMasters()).thenReturn(Collections.emptyList());
        when(procedureService.getAllProcedure()).thenReturn(Collections.emptyList());

        when(resources.getForward("ShowSlotSearchPage")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

    @Test
    public void executeShouldProperlyCallLocalMethods(){
        List<SlotDto> list = Collections.singletonList(SlotDto.builder()
                .slot(Slot.builder().id(9L).build())
                .build());
        form.setStatus(Status.BOOKED);
        form.setMasterId(TEST_MASTER_ID);
        form.setProcedure(TEST_PROCEDURE_ID);
        form.setMinDate(TEST_MIN_DATE);
        form.setMaxDate(TEST_MAX_DATE);
        form.setMinStartTime(TEST_MIN_TIME);
        form.setMaxStartTime(TEST_MAX_TIME);

        doReturn(list).when(action).getSlotsList(eq(request), eq(TEST_MASTER_ID),
                any(Status.class), eq(TEST_PROCEDURE_ID), eq(TEST_MIN_DATE), eq(TEST_MAX_DATE),
                eq(TEST_MIN_TIME), eq(TEST_MAX_TIME), eq(slotService), any(PaginationHelper.class));
        doNothing().when(action).setRequestAttributes(eq(request), anyLong(), any(Status.class), anyLong(), any(LocalDate.class),
                any(LocalDate.class), any(LocalTime.class), any(LocalTime.class), anyList(), any(PaginationHelper.class));
        doNothing().when(action).addPaginationToRequest(eq(request), eq(slotService), eq(TEST_MASTER_ID),
                any(Status.class), eq(TEST_PROCEDURE_ID), eq(TEST_MIN_DATE), eq(TEST_MAX_DATE),
                eq(TEST_MIN_TIME), eq(TEST_MAX_TIME), any(PaginationHelper.class));

        action.execute(request, response, form, resources);

        verify(action).setRequestAttributes(eq(request), anyLong(), any(Status.class), anyLong(), any(LocalDate.class),
            any(LocalDate.class), any(LocalTime.class), any(LocalTime.class), anyList(), any(PaginationHelper.class));
        verify(action).addPaginationToRequest(eq(request), eq(slotService), eq(TEST_MASTER_ID), any(Status.class),
                eq(TEST_PROCEDURE_ID), eq(TEST_MIN_DATE), eq(TEST_MAX_DATE), eq(TEST_MIN_TIME), eq(TEST_MAX_TIME), any(PaginationHelper.class));
    }

    @Test
    public void setRequestAttributesShouldProperlySetAttributes() {
        action.setRequestAttributes(request, TEST_MASTER_ID, Status.BOOKED, TEST_PROCEDURE_ID, TEST_MIN_DATE,
                TEST_MAX_DATE, TEST_MIN_TIME, TEST_MAX_TIME, Collections.emptyList(), helper);

        verify(request).setAttribute("masterId", TEST_MASTER_ID);
        verify(request).setAttribute("procedureId", TEST_PROCEDURE_ID);
        verify(request).setAttribute("minDate", TEST_MIN_DATE);
        verify(request).setAttribute("maxDate", TEST_MAX_DATE);
        verify(request).setAttribute("minTime", TEST_MIN_TIME);
        verify(request).setAttribute("maxTime", TEST_MAX_TIME);
        verify(request).setAttribute("slots", Collections.emptyList());
        verify(session).setAttribute("masters", Collections.emptyList());
        verify(session).setAttribute("procedures", Collections.emptyList());
    }

    @Test
    public void getSlotsListShouldProperlyGetList() {
        when(helper.getRecordsPerPage()).thenReturn(10);
        when(helper.getPreviousRecordNumber(request, 10)).thenReturn(20);

        action.getSlotsList(request, TEST_MASTER_ID, Status.BOOKED, TEST_PROCEDURE_ID, TEST_MIN_DATE,
                TEST_MAX_DATE, TEST_MIN_TIME, TEST_MAX_TIME, slotService, helper);
        verify(slotService).findSlots(TEST_MASTER_ID, Status.BOOKED, 0L, TEST_PROCEDURE_ID, TEST_MIN_DATE,
                TEST_MAX_DATE, TEST_MIN_TIME, TEST_MAX_TIME, 10, 20);
    }

    @Test
    public void addPaginationToRequestShouldProperlyAddPagination() {
        when(slotService.getSlotSearchResultCount(TEST_MASTER_ID, Status.BOOKED, 0L, TEST_PROCEDURE_ID, TEST_MIN_DATE,
                TEST_MAX_DATE, TEST_MIN_TIME, TEST_MAX_TIME)).thenReturn(43L);
        action.addPaginationToRequest(request, slotService, TEST_MASTER_ID, Status.BOOKED, TEST_PROCEDURE_ID, TEST_MIN_DATE,
                TEST_MAX_DATE, TEST_MIN_TIME, TEST_MAX_TIME, helper);
        verify(helper).addPaginationToRequest(request, 43L);
    }

}