package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.SlotDao;
import com.saloon.beauty.repository.dao.SlotDtoDao;
import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SlotServiceTest {

    @Mock
    SlotService mockService;

    @Mock
    DaoManager mockDaoManager;

    @Mock
    SlotDao mockSlotDao;

    @Mock
    SlotDtoDao mockSlotDtoDao;

    @Mock
    Slot mockSlot;

    @Mock
    SlotDto mockSlotDto;


    @Before
    public void initSetUp() throws SQLException {
        mockService = spy(new SlotService(new DaoManagerFactory()));
        when(mockDaoManager.getSlotDao()).thenReturn(mockSlotDao);
        when(mockDaoManager.getSlotDtoDao()).thenReturn(mockSlotDtoDao);
    }


    @Test
    public void addNewSlotCommandShouldReturnPositiveDigit() throws SQLException {
        final long TEST_SLOT_ID = 3L;
        when(mockSlotDao.save(any(Slot.class))).thenReturn(TEST_SLOT_ID);

        long result = mockService.addNewSlotCommand(mockDaoManager, mockSlot);

        assertEquals(TEST_SLOT_ID, result);
    }

    @Test
    public void updateSlotStatusCommandShouldReturnTrue() throws SQLException {
        final Status TEST_STATUS = Status.BOOKED;
        final long TEST_USER_ID = 1L;
        final long TEST_SLOT_ID = 1L;

        when(mockSlotDao.get(TEST_SLOT_ID)).thenReturn(Optional.of(mockSlot));
        when(mockSlot.getStatus()).thenReturn(Status.FREE);
        when(mockSlot.getUser()).thenReturn(0L);
        when(mockSlot.getId()).thenReturn(1L);
        when(mockSlot.getDate()).thenReturn(LocalDate.now().plusMonths(1));

        boolean result = mockService.updateSlotStatusCommand(mockDaoManager, TEST_SLOT_ID, TEST_USER_ID, TEST_STATUS);

        verify(mockSlotDao).get(anyLong());
        verify(mockSlotDao).updateSlotStatus(TEST_SLOT_ID, TEST_STATUS, TEST_USER_ID);

        assertTrue(result);
    }

}