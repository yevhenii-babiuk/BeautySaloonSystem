package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.entity.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class FeedbackService extends Service {

    private DaoManagerFactory daoManagerFactory;

    public FeedbackService(DaoManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }

    public long addFeedbackToSlot(long slotId, String feedbackText) {

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Feedback feedback = Feedback.builder()
                .slot(slotId)
                .text(feedbackText)
                .build();

        Object executionResult = daoManager.executeTransaction(manager -> addFeedbackToSlotCommand(manager, feedback));

        return checkAndCastObjectToLong(executionResult);
    }

    public Optional<Feedback> getFeedbackById(long feedbackId) {

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager -> manager.getFeedbackDao().get(feedbackId));

        return checkAndCastObjectToOptional(executingResult);
    }

    public Optional<Feedback> getFeedbackBySlot(long slotId) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager -> manager.getFeedbackDao().getFeedbackBySlot(slotId));

        return checkAndCastObjectToOptional(executingResult);
    }

    public List<Feedback> findFeedback(long masterId, long procedureId,
                                       int recordsQuantity, int previousRecordNumber) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> findFeedbackCommand(manager, masterId, procedureId,
                recordsQuantity, previousRecordNumber));

        return checkAndCastObjectToList(executionResult);
    }


    public long getFeedbackSearchResultCount(long masterId, long procedureId) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager ->
                manager.getFeedbackDao().getFeedbackSearchResultCount(masterId, procedureId));

        return checkAndCastObjectToLong(executionResult);
    }


    //Commands which is needed to be executed in corresponding public service methods
    long addFeedbackToSlotCommand(DaoManager manager, Feedback feedback) throws SQLException {

        Optional<Slot> slotOptional = manager.getSlotDao().get(feedback.getSlot());
        Slot slot = null;

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

    List<Feedback> findFeedbackCommand(DaoManager manager, long masterId, long procedureId,
                                       int recordsQuantity, int previousRecordNumber) throws SQLException {

        List<Feedback> feedbackList = manager.getFeedbackDao().getAllFeedbackParameterized(masterId, procedureId,
                recordsQuantity, previousRecordNumber);

        return feedbackList;
    }

}
