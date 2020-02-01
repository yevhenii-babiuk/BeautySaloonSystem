package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Dao for Slot entity
 */
public interface SlotDao extends Dao<Slot> {


    /**
     * Counts amount of all slot in a DB according to search parameters(with all ar with any of them)
     *
     * @param masterId    - master`s ID
     * @param status      - slot`s status
     * @param userId      - procedure`s ID
     * @param procedureId - procedure`s ID
     * @param minDate     - minimum limit of date for find slots
     * @param maxDate     - maximum limit of date for find slots
     * @param minTime     - minimum limit of time for find slots
     * @param maxTime     - maximum limit of time for find slots
     * @param feedbackIsPresent - does slot has feedback
     * @return amount of all slots correspond to the given criteria
     */
    long getSlotSearchResultCount(long masterId, Status status, long userId, long procedureId,
                                  LocalDate minDate, LocalDate maxDate,
                                  LocalTime minTime, LocalTime maxTime,
                                  boolean feedbackIsPresent);

    /**
     * Finds all slot in a DB according to search parameters(with all ar with any of them)
     *
     * @param masterId    - master`s ID
     * @param status      - slot`s status
     * @param userId      - procedure`s ID
     * @param procedureId - procedure`s ID
     * @param minDate     - minimum limit of date for find slots
     * @param maxDate     - maximum limit of date for find slots
     * @param minTime     - minimum limit of time for find slots
     * @param maxTime     - maximum limit of time for find slots
     * @param feedbackIsPresent - does slot has feedback
     * @param limit       the number of slot
     * @param offset      the number of slot
     * @return - list with all slots correspond to the given criteria
     */
    List<Slot> getAllSlotParameterized(long masterId, Status status, long userId, long procedureId,
                                       LocalDate minDate, LocalDate maxDate,
                                       LocalTime minTime, LocalTime maxTime,
                                       boolean feedbackIsPresent,
                                       int limit, int offset);

    /**
     * Find all slots of by such status
     *
     * @param status - boolean type of slot`s status
     * @return {@code List} of slots
     */
    List<Slot> getSlotByStatusFeedbackRequest(boolean status);

    /**
     * Method for update status target slot
     *
     * @param id     - identifier of target slot
     * @param status - new status of updating slot
     * @param userId - identifier of user, that want update status of slot
     */
    void updateSlotStatus(long id, Status status, long userId);

    /**
     * Update slot`s feedback request status
     *
     * @param slotId - an ID of target slot
     * @param status - new status of target slot
     */
    void updateFeedbackRequestStatus(long slotId, boolean status);

}
