package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackDao extends Dao<Feedback>{

    List<Feedback> getFeedbackByMaster(String master);

    Optional<Feedback> getFeedbackBySlot(long id);
}
