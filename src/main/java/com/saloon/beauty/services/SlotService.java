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

/**
 * Service class which has methods bound with Slot operations
 * and DAO
 */
public class SlotService extends Service {

    private DaoManagerFactory daoManagerFactory;

    public SlotService(DaoManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }


    /**
     * Method adding new slot
     * @param date - date of new slot
     * @param startTime - start time of new slot
     * @param endTime - end time of new slot
     * @param masterId - long type of master`s identifier
     * @param userId - long type of user`s identifier (can be 0L)
     * @param procedureId - long type of procedure
     * @return long type of identifier of new slot
     */
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

    /**
     * Method for update target slot
     * @param slotId - identifier of target slot
     * @param date - date of updated slot
     * @param startTime - start time of updated slot
     * @param endTime - end time of updated slot
     * @param masterId - long type of master`s identifier
     * @param procedureId - long type of procedure
     * @return boolean type result of updating slot
     */
    public boolean updateSlot(long slotId,
                           long masterId,
                           long procedureId,
                           LocalDate date,
                           LocalTime startTime,
                           LocalTime endTime) {

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeTransaction(manager -> updateSlotCommand(manager, slotId, masterId, procedureId,
                date, startTime, endTime));
        return checkAndCastExecutingResult(executionResult);
    }

    /**
     * Method for update status target slot
     * @param slotId - identifier of target slot
     * @param userId - identifier of user, that want update status of slot
     * @param isBooked  - boolean type of wanted action with target slot
     *                  true for booking or false for quitting slot
     * @return boolean type result of updating slot
     */
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


    /**
     * Finds all slots which fits of the given combinations
     * of criteria: master, status, user, procedure, date and time.
     * Any of this parameters may present or may not.
     * @param masterId - master`s ID target slot
     * @param status - status of target slot
     * @param userId - users`s ID of target slot
     * @param procedureId - procedure`s ID of target slot
     * @param minDate - minimum limit of date for find slots
     * @param maxDate - maximum limit of date for find slots
     * @param minTime - minimum limit of time for find slots
     * @param maxTime - maximum limit of time for find slots
     * @param feedbackIsPresent - does need slots with feedback
     * @param recordsQuantity - quantity of records per page
     * @param previousRecordNumber - number of previous record
     * @return a list with {@code SloDto} contains target slots
     */
    public List<SlotDto> findSlots(long masterId, Status status, long userId, long procedureId,
                                   LocalDate minDate, LocalDate maxDate,
                                   LocalTime minTime, LocalTime maxTime,
                                   boolean feedbackIsPresent,
                                   int recordsQuantity, int previousRecordNumber) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> findSlotsCommand(manager, masterId, status, userId, procedureId, minDate, maxDate,
                minTime, maxTime, feedbackIsPresent, recordsQuantity, previousRecordNumber));

        return checkAndCastObjectToList(executionResult);
    }

    /**
     * Method, that returns {@code SlotDto} if such exist by identifier
     * @param slotId - identifier of required slot
     * @return {@code Optional} of required slot
     */
    public Optional<SlotDto> getSlotDtoById(long slotId) {

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> getSlotDtoByIdCommand(manager, slotId));

        return checkAndCastObjectToOptional(executionResult);
    }

    /**
     * Counts all slots in search result which fits to the given combinations
     * of criteria:
     * @param masterId - master`s ID target slot
     * @param status - status of target slot
     * @param userId - users`s ID of target slot
     * @param procedureId - procedure`s ID of target slot
     * @param minDate - minimum limit of date for find slots
     * @param maxDate - maximum limit of date for find slots
     * @param minTime - minimum limit of time for find slots
     * @param maxTime - maximum limit of time for find slots
     * @param feedbackIsPresent - does need slots with feedback
     * @return quantity of all slots in search result
     */
    public long getSlotSearchResultCount(long masterId, Status status, long userId, long procedureId,
                                         LocalDate minDate, LocalDate maxDate,
                                         LocalTime minTime, LocalTime maxTime,
                                         boolean feedbackIsPresent) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager ->
                manager.getSlotDao().getSlotSearchResultCount(masterId, status, userId, procedureId, minDate, maxDate,
                        minTime, maxTime, feedbackIsPresent));

        return checkAndCastObjectToLong(executionResult);
    }

    /**
     * Method, that returns {@code List} of {@code SlotDto} by status
     * @param status - boolean type of slot`s status
     * @return {@code List} of slots
     */
    public List<Slot> getSlotByStatusFeedbackRequest(boolean status) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager -> manager.getSlotDao().getSlotByStatusFeedbackRequest(status));

        return checkAndCastObjectToList(executingResult);
    }

    /**
     * Update slot`s feedback request status to true
     * @param slotId - an ID of target slot
     * @return boolean type of updating result
     */
    public boolean setFeedbackRequestStatusTrue(long slotId) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> setSlotFeedbackRequestStatusTrueCommand(manager, slotId, true));

        return checkAndCastExecutingResult(executionResult);
    }


    //Commands which is needed to be executed in corresponding public service methods
    synchronized long addNewSlotCommand(DaoManager manager, Slot slot) throws SQLException {
        long slotId = manager.getSlotDao().save(slot);

        if (slotId > 0) {
            return slotId;
        } else {
            return 0;
        }
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

        if (slot.getDate().isBefore(LocalDate.now())) {
            return EXECUTING_FAILED;
        } else if (slot.getDate().equals(LocalDate.now())) {
            if (slot.getEndTime().isBefore(LocalTime.now()) ||
                    slot.getStartTime().isBefore(LocalTime.now())) {
                return EXECUTING_FAILED;
            }
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

    List<SlotDto> findSlotsCommand(DaoManager manager, long masterId, Status status, long userId, long procedureId,
                                   LocalDate minDate, LocalDate maxDate, LocalTime minTime, LocalTime maxTime,
                                   boolean feedbackIsPresent,
                                   int recordsQuantity, int previousRecordNumber) throws SQLException {

        SlotDao slotDao = manager.getSlotDao();
        List<Slot> slotList = slotDao.getAllSlotParameterized(masterId, status, userId, procedureId, minDate, maxDate,
                minTime, maxTime, feedbackIsPresent, recordsQuantity, previousRecordNumber);


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
        Slot slot;
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

    synchronized boolean updateSlotCommand(DaoManager manager, long slotId, long masterId, long procedureId,
                                           LocalDate date, LocalTime startTime, LocalTime endTime) throws SQLException {
        SlotDao slotDao = manager.getSlotDao();

        Optional<Slot> slotOptional = slotDao.get(slotId);
        Slot slot;

        if (slotOptional.isPresent()) {
            slot = slotOptional.get();
        } else {
            return EXECUTING_FAILED;
        }

        slot.setMaster(masterId);
        slot.setProcedure(procedureId);
        slot.setDate(date);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);

        slotDao.update(slot);

        if (slot.equals(slotDao.get(slotId).get())) {
            return EXECUTING_SUCCESSFUL;
        } else {
            return EXECUTING_FAILED;
        }

    }


    /**
     * Creates a {@code SlotDto} (with Feedback, User and Master) from given slot.
     * @param manager - {@code DaoManager} for accessing daos needed
     * @param slot - slot from wich DTO is need to created
     * @return the object with contains given slot and its feedback, user, and master
     * @throws SQLException if manager can't give a Dao needed
     */
    SlotDto createSlotDtoFromSlot(DaoManager manager, Slot slot) throws SQLException {
        SlotDtoDao slotDtoDao = manager.getSlotDtoDao();

        return slotDtoDao.getFullInformation(slot.getId()).get();
    }
}
