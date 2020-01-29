package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.FeedbackDao;
import com.saloon.beauty.repository.dao.SlotDao;
import com.saloon.beauty.repository.entity.Feedback;

import com.saloon.beauty.repository.entity.Slot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceTest {

    @Mock
    FeedbackService mockService;

    @Mock
    DaoManager mockDaoManager;

    @Mock
    FeedbackDao mockFeedbackDao;

    @Mock
    SlotDao mockSlotDao;

    @Mock
    Feedback mockFeedback;

    @Mock
    Slot mockSlot;

    @Before
    public void initSetUp() throws SQLException {
        mockService = spy(new FeedbackService(new DaoManagerFactory()));
        when(mockDaoManager.getFeedbackDao()).thenReturn(mockFeedbackDao);
        when(mockDaoManager.getSlotDao()).thenReturn(mockSlotDao);
    }

    @Test
    public void addFeedbackToSlotCommandShouldSaveFeedbackAndReturnPositiveId() throws SQLException {
        final long TEST_FEEDBACK_ID = 3L;
        when(mockFeedbackDao.save(any(Feedback.class))).thenReturn(TEST_FEEDBACK_ID);
        when(mockFeedback.getSlot()).thenReturn(2L);
        when(mockSlotDao.get(2L)).thenReturn(Optional.of(mockSlot));
        when(mockSlot.getDate()).thenReturn(LocalDate.now().minusDays(1));

        long result = mockService.addFeedbackToSlotCommand(mockDaoManager, mockFeedback);
        verify(mockFeedbackDao).save(mockFeedback);
        assertEquals(TEST_FEEDBACK_ID ,result);
    }

    @Test
    public void addFeedbackToSlotCommandShouldSaveFeedbackAndReturnNegativeResult() throws SQLException {
        when(mockFeedback.getSlot()).thenReturn(2L);
        when(mockSlotDao.get(2L)).thenReturn(Optional.of(mockSlot));
        when(mockSlot.getDate()).thenReturn(LocalDate.now().plusDays(1));

        long result = mockService.addFeedbackToSlotCommand(mockDaoManager, mockFeedback);
        verify(mockSlotDao, times(1)).get(anyLong());
        verify(mockFeedbackDao, never()).save(mockFeedback);
        assertEquals(0 ,result);
    }
}