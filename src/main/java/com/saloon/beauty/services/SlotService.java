package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.SlotDao;
import com.saloon.beauty.repository.dao.SlotDtoDao;
import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlotService extends Service {

    private DaoManagerFactory daoManagerFactory;

    public SlotService(DaoManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }

    public long addNewSlot(LocalDate date,
                           LocalTime startTime,
                           LocalTime endTime,
                           long masterId,
                           long userId,
                           long procedureId) {

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Slot slot = Slot.builder()
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .master(masterId)
                .user(userId)
                .procedure(procedureId)
                .status(Status.FREE)
                .build();

        Object executionResult = daoManager.executeTransaction(manager -> addNewSlotCommand(manager, slot));

        return checkAndCastObjectToLong(executionResult);

    }

    public Optional<Slot> getSlotById(long slotId) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager -> manager.getSlotDao().get(slotId));

        return checkAndCastObjectToOptional(executingResult);
    }

    public boolean updateSlotStatus(long slotId, long userId, boolean isBooked) {

        Status status;

        if (isBooked) {
            status = Status.BOOKED;
        } else {
            status = Status.FREE;
        }

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> updateSlotStatusCommand(manager, slotId, userId, status));

        return checkAndCastExecutingResult(executionResult);
    }

    public List<SlotDto> findSlots(long masterId, Status status, long procedureId,
                                   LocalDate minDate, LocalDate maxDate,
                                   LocalTime minTime, LocalTime maxTime,
                                   int recordsQuantity, int previousRecordNumber) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> findSlotsCommand(manager, masterId, status, procedureId, minDate, maxDate,
                minTime, maxTime, recordsQuantity, previousRecordNumber));

        return checkAndCastObjectToList(executionResult);
    }

    public Optional<SlotDto> getSlotDtoById(long slotId) {

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> getSlotDtoByIdCommand(manager, slotId));

        return checkAndCastObjectToOptional(executionResult);
    }

    /**
     * Counts all slots in search result
     *
     * @param masterId
     * @param status
     * @param minDate
     * @param maxDate
     * @param minTime
     * @param maxTime
     * @return quantity of all slots in search result
     */
    public long getSlotSearchResultCount(long masterId, Status status, long procedureId,
                                         LocalDate minDate, LocalDate maxDate,
                                         LocalTime minTime, LocalTime maxTime) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager ->
                manager.getSlotDao().getSlotSearchResultCount(masterId, status, procedureId, minDate, maxDate,
                        minTime, maxTime));

        return checkAndCastObjectToLong(executionResult);
    }


    public List<Slot> getSlotByStatusFeedbackRequest(boolean status) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager -> manager.getSlotDao().getSlotByStatusFeedbackRequest(status));

        return checkAndCastObjectToList(executingResult);
    }

    public boolean setFeedbackRequestStatusTrue(long slotId) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> setSlotFeedbackRequestStatusTrueCommand(manager, slotId, true));

        return checkAndCastExecutingResult(executionResult);
    }

    //Commands which is needed to be executed in corresponding public service methods
    synchronized long addNewSlotCommand(DaoManager manager, Slot slot) throws SQLException {
        long slotId = manager.getSlotDao().save(slot);

        slot.setId(slotId);

        return slotId;
    }

    synchronized boolean updateSlotStatusCommand(DaoManager manager, long slotId, long userId, Status status) throws SQLException {
        SlotDao slotDao = manager.getSlotDao();

        Optional<Slot> slotOptional = slotDao.get(slotId);

        Slot slot;

        if (slotOptional.isPresent()) {
            slot = slotOptional.get();
            if (slot.getStatus().equals(status) && slot.getUser() != userId) {
                return EXECUTING_FAILED;
            }
        } else {
            return EXECUTING_FAILED;
        }

        if (status.equals(Status.FREE) && slot.getUser() == userId) {
            slotDao.updateSlotStatus(slot.getId(), status, 0L);
        } else if (status.equals(Status.BOOKED) && slot.getUser() == 0) {
            slotDao.updateSlotStatus(slot.getId(), status, userId);
        } else {
            return EXECUTING_FAILED;
        }

        return EXECUTING_SUCCESSFUL;
    }

    List<SlotDto> findSlotsCommand(DaoManager manager, long masterId, Status status, long procedureId, LocalDate minDate,
                                   LocalDate maxDate, LocalTime minTime, LocalTime maxTime,
                                   int recordsQuantity, int previousRecordNumber) throws SQLException {

        SlotDao slotDao = manager.getSlotDao();
        List<Slot> slotList = slotDao.getAllSlotParameterized(masterId, status, procedureId, minDate, maxDate,
                minTime, maxTime, recordsQuantity, previousRecordNumber);


        List<SlotDto> slotDtos = new ArrayList<>();
        for (Slot slot :
                slotList) {
            slotDtos.add(createSlotDtoFromSlot(manager, slot));
        }
        return slotDtos;
    }

    Optional<SlotDto> getSlotDtoByIdCommand(DaoManager manager, long slotId) throws SQLException {
        SlotDao slotDao = manager.getSlotDao();
        Optional<Slot> slot = slotDao.get(slotId);

        if (slot.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(createSlotDtoFromSlot(manager, slot.get()));
    }

    synchronized boolean setSlotFeedbackRequestStatusTrueCommand(DaoManager manager, long slotId, boolean status) throws SQLException {
        Optional<Slot> slotOptional = manager.getSlotDao().get(slotId);
        Slot slot = null;
        if (slotOptional.isEmpty()) {
            return EXECUTING_FAILED;
        } else {
            slot = slotOptional.get();
        }

        if (slot.isFeedbackRequest()) {
            return EXECUTING_FAILED;
        }

        manager.getSlotDao().updateFeedbackRequestStatus(slotId, status);
        return EXECUTING_SUCCESSFUL;
    }

    SlotDto createSlotDtoFromSlot(DaoManager manager, Slot slot) throws SQLException {
        SlotDtoDao slotDtoDao = manager.getSlotDtoDao();

        return slotDtoDao.getFullInformation(slot.getId()).orElse(null);
    }
}
