package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DBUtils;
import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.FeedbackDao;
import com.saloon.beauty.repository.entity.Feedback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeedbackDaoImpl implements FeedbackDao {

    private static final Logger LOG = LogManager.getLogger(FeedbackDao.class);

    private Connection connection;

    public FeedbackDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Feedback> getFeedbackByMaster(String master, int limit, int offset) {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_FEEDBACK_BY_MASTER_QUERY);
            statement.setString(1, master);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                feedbacks.add(getFeedbackFromResultRow(resultSet));
            }
            resultSet.close();

        } catch (SQLException e) {
            String errorText = String.format("Can't get an feedback by master: %s. Cause: %s.", master, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
        return feedbacks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Feedback> getFeedbackBySlot(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_FEEDBACK_BY_SLOT_QUERY);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(getFeedbackFromResultRow(rs));
            } else {
                return Optional.empty();
            }


        } catch (SQLException e) {
            String errorText = String.format("Can't get an feedback by slot: %s. Cause: %s.", id, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Feedback> get(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_FEEDBACK_QUERY);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(getFeedbackFromResultRow(rs));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            String errorText = String.format("Can't get an feedback by id: %s. Cause: %s.", id, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Feedback> getAll() {
        List<Feedback> feedbacks = new ArrayList<>();

        try {
            PreparedStatement selectStatement = connection
                    .prepareStatement(DBQueries.GET_ALL_FEEDBACK_QUERY);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                feedbacks.add(getFeedbackFromResultRow(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            String errorText = "Can't get feedback list from DB. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }

        return feedbacks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long save(Feedback feedback) {
        try {
            PreparedStatement saveStatement = connection
                    .prepareStatement(DBQueries.SAVE_FEEDBACK_QUERY, Statement.RETURN_GENERATED_KEYS);
            saveStatement.setLong(1, feedback.getSlot());
            saveStatement.setString(2, feedback.getText());

            saveStatement.executeUpdate();

            return DBUtils.getIdFromStatement(saveStatement);

        } catch (SQLException e) {
            if (DBUtils.isTryingToInsertDuplicate(e)) {
                return -1;
            } else {
                String errorText = String.format("Can't save feedback: %s. Cause: %s", feedback, e.getMessage());
                LOG.error(errorText, e);
                throw new DaoException(errorText, e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Feedback feedback) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement(DBQueries.UPDATE_FEEDBACK_INFO_QUERY);
            updateStatement.setLong(1, feedback.getSlot());
            updateStatement.setString(2, feedback.getText());
            updateStatement.setLong(3, feedback.getId());

            updateStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't update feedback: %s. Cause: %s", feedback, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Feedback feedback) {
        try {
            PreparedStatement deleteStatement = connection
                    .prepareStatement(DBQueries.DELETE_FEEDBACK_QUERY);
            deleteStatement.setLong(1, feedback.getId());

            deleteStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't delete feedback: %s. Cause: %s", feedback, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * Creates an feedback from given {@code ResultSet}
     *
     * @param resultSet - {@code ResultSet} with feedback data
     * @return - feedback was passed into
     * @throws SQLException if the columnLabels is not valid;
     *                      if a database access error occurs or result set is closed
     */
    Feedback getFeedbackFromResultRow(ResultSet resultSet) throws SQLException {
        return Feedback.builder()
                .id(resultSet.getLong("feedback_id"))
                .slot(resultSet.getLong("slot"))
                .text(resultSet.getString("text"))
                .build();
    }
}
