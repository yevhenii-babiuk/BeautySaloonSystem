package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.FeedbackDao;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.entity.Feedback;
import com.saloon.beauty.repository.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeedbackDaoImpl implements FeedbackDao {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private Connection connection;

    public FeedbackDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Feedback> getFeedbackByMaster(String master) {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_FEEDBACK_BY_MASTER_QUERY);
            statement.setString(1, master);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                feedbacks.add(getFeedbackFromResultRow(resultSet));
            }
            resultSet.close();

        } catch (SQLException e) {
            String errorText = String.format("Can't get an feedback by master: %s. Cause: %s.", master , e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
        return feedbacks;
    }

    @Override
    public Optional<Feedback> getFeedbackBySlot(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Feedback> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Feedback> getAll() {
        return null;
    }

    @Override
    public long save(Feedback feedback) {
        return 0;
    }

    @Override
    public void update(Feedback feedback) {

    }

    @Override
    public void delete(Feedback feedback) {

    }

    /**
     * Creates an feedback from given {@code ResultSet}
     *
     * @param rs - {@code ResultSet} with users data
     * @return - feedback whose email & password was passed into
     * @throws SQLException if the columnLabels is not valid;
     *                      if a database access error occurs or result set is closed
     */
    Feedback getFeedbackFromResultRow(ResultSet rs) throws SQLException {
        return Feedback.builder()
                .id(rs.getLong("user_id"))
                .slot(rs.getLong("slot"))
                .text(rs.getString("text"))
                .build();
    }
}
