package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackDao extends Dao<Feedback>{

    Optional<Feedback> getFeedbackBySlot(long id);

    List<Feedback> getAllFeedbackParameterized(long masterId, long procedureId,
                                               int limit, int offset);

    long getFeedbackSearchResultCount(long masterId, long procedureId);
}
