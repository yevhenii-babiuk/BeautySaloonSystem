package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotDao extends Dao<Slot>{


    long getBooSearchResultCount(long masterId, Status status,
                                 LocalDate minDate, LocalDate maxDate,
                                 LocalTime minTime, LocalTime maxTime);

    List<Slot> getAllSlotParameterized(long masterId, Status status,
                                       LocalDate minDate, LocalDate maxDate,
                                       LocalTime minTime, LocalTime maxTime,
                                       int limit, int offset);

    void updateSlotStatus(long id, Status status);
}
