package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Feedback;

import java.util.List;
import java.util.Optional;

/**
 * Dao for Feedback entity
 */
public interface FeedbackDao extends Dao<Feedback>{

    /**
     * Finds all feedback in a DB according to search parameters(with all ar with any of them)
     * @param masterId - master`s ID
     * @param procedureId - procedure`s ID
     * @param limit the number of feedback
     * @param offset the number of feedback
     * @return - list with all feedback correspond to the given criteria
     */
    List<Feedback> getAllFeedbackParameterized(long masterId, long procedureId,
                                               int limit, int offset);

    /**
     * Counts amount of all feedback which fits target parameters
     * @param masterId - master`s ID
     * @param procedureId - procedure`s ID
     * @return amount of all feedback which fits target parameters
     */
    long getFeedbackSearchResultCount(long masterId, long procedureId);
}
