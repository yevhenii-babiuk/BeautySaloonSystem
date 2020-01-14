package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotDao extends Dao<Slot>{


    long getSlotSearchResultCount(long masterId, Status status, long userId, long procedureId,
                                  LocalDate minDate, LocalDate maxDate,
                                  LocalTime minTime, LocalTime maxTime);

    List<Slot> getAllSlotParameterized(long masterId, Status status, long userId, long procedureId,
                                       LocalDate minDate, LocalDate maxDate,
                                       LocalTime minTime, LocalTime maxTime,
                                       int limit, int offset);

    List<Slot> getSlotByStatusFeedbackRequest(boolean status);

    void updateSlotStatus(long id, Status status, long userId);

    void updateFeedbackRequestStatus(long slotId, boolean status);

}
