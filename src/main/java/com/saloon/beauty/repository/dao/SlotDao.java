package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotDao extends Dao<Slot>{

    List<Slot> getSlotByDataTime(LocalDate minDate, LocalDate maxDate, LocalTime minTime, LocalTime maxTime);

    List<Slot> getSlotByMaster(String master);

    List<Slot> getSlotByStatus(String status);

    void updateSlotStatus(long id, Status status);
}
