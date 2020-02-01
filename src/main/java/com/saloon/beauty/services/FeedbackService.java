package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.entity.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Service class which has methods bound with Feedback operations
 * and DAO
 */
public class FeedbackService extends Service {

    private DaoManagerFactory daoManagerFactory;

    public FeedbackService(DaoManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }

    /**
     * Adds a feedback to such slot
     * @param slotId - identifier of slot to adding feedback
     * @param feedbackText - text of user`s feedback
     * @return identifier of feedback, that user added
     */
    public long addFeedbackToSlot(long slotId, String feedbackText) {

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Feedback feedback = Feedback.builder()
                .slot(slotId)
                .text(feedbackText)
                .build();

        Object executionResult = daoManager.executeTransaction(manager -> addFeedbackToSlotCommand(manager, feedback));

        return checkAndCastObjectToLong(executionResult);
    }

    //Commands which is needed to be executed in corresponding public service methods
    long addFeedbackToSlotCommand(DaoManager manager, Feedback feedback) throws SQLException {

        Optional<Slot> slotOptional = manager.getSlotDao().get(feedback.getSlot());
        Slot slot;

        if (slotOptional.isPresent()) {
            slot = slotOptional.get();
        } else {
            return -2;
        }

        if (slot.getDate().isAfter(LocalDate.now())) {
            return 0;
        } else if (slot.getDate().equals(LocalDate.now())) {
            if (slot.getEndTime().isAfter(LocalTime.now())) {
                return 0;
            }
        }

        long feedbackId = manager.getFeedbackDao().save(feedback);

        if (feedbackId < 0) {
            //There is such a feedback in the DB
            return feedbackId;
        } else {
            feedback.setId(feedbackId);
        }

        return feedbackId;
    }

}
