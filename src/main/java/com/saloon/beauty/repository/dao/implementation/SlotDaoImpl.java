package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DBUtils;
import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.SlotDao;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlotDaoImpl implements SlotDao {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private Connection connection;

    public SlotDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gives {@code PreparedStatement} depending on data
     * existence in every of three methods argument
     * @param masterId - the master'd ID
     * @param status - the slot status
     * @param minDate - minimum boundary of searching by date
     * @param maxDate - maximum boundary of searching by date
     * @param minTime - minimum boundary of searching by time
     * @param maxTime - maximum boundary of searching by time
     * @param limit        the number of loans returned
     * @param offset       the number of loans returned
     * @param rowsCounting defines type of result {@code Statement}.
     *                     If {@code rowsCounting} is {true} statement
     *                     will return count of all target books.
     *                     It will return only limited count of books otherwise.
     * @return - statement for getting books information from DB
     */
    private PreparedStatement getPreparedAllSlotStatement(long masterId, Status status,
                                                          LocalDate minDate, LocalDate maxDate,
                                                          LocalTime minTime, LocalTime maxTime,
                                                          int limit, int offset, boolean rowsCounting) throws SQLException {

        StringBuilder queryBuilder = new StringBuilder();
        boolean anotherParameter = false;

        if (rowsCounting) {
            queryBuilder.append(DBQueries.ALL_SLOTS_COUNT_QUERY_HEAD_PART);
        } else {
            queryBuilder.append(DBQueries.ALL_SLOTS_QUERY_HEAD_PART);
        }

        if (masterId > 0 || status.equals(Status.FREE) || minDate != null ||
                maxDate != null || minTime != null || maxTime != null) {
            queryBuilder.append(" WHERE");
        }

        if (masterId > 0) {
            if (anotherParameter) {
                queryBuilder.append(" AND ");
            } else {
                queryBuilder.append(" ");
            }
            anotherParameter = true;
            queryBuilder.append(DBQueries.ALL_SLOTS_QUERY_MASTER_PART);

        }

        if (status.equals(Status.FREE)) {
            if (anotherParameter) {
                queryBuilder.append(" AND ");
            } else {
                queryBuilder.append(" ");
            }
            anotherParameter = true;
            queryBuilder.append(DBQueries.ALL_SLOTS_QUERY_STATUS_PART);
        }

        if (minDate != null) {
            if (anotherParameter) {
                queryBuilder.append(" AND ");
            } else {
                queryBuilder.append(" ");
            }
            anotherParameter = true;
            queryBuilder.append(DBQueries.ALL_SLOTS_QUERY_MIN_DATE_PART);
        }
        if (maxDate != null) {
            if (anotherParameter) {
                queryBuilder.append(" AND ");
            } else {
                queryBuilder.append(" ");
            }
            anotherParameter = true;
            queryBuilder.append(DBQueries.ALL_SLOTS_QUERY_MAX_DATE_PART);
        }

        if (minTime != null) {
            if (anotherParameter) {
                queryBuilder.append(" AND ");
            } else {
                queryBuilder.append(" ");
            }
            anotherParameter = true;
            queryBuilder.append(DBQueries.ALL_SLOTS_QUERY_MIN_TIME_PART);
        }
        if (maxTime != null) {
            if (anotherParameter) {
                queryBuilder.append(" AND ");
            } else {
                queryBuilder.append(" ");
            }
            queryBuilder.append(DBQueries.ALL_SLOTS_QUERY_MAX_TIME_PART);
        }

        if (rowsCounting) {
            queryBuilder.append(" ").append(DBQueries.ALL_SLOTS_COUNT_QUERY_TAIL_PART);
        } else {
            queryBuilder.append(" ").append(DBQueries.ALL_SLOTS_QUERY_TAIL_PART);
        }

        PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

        int parameterIndex = 1;
        if (masterId > 0) {
            statement.setLong(parameterIndex++, masterId);
        }

        if (status.equals(Status.FREE)) {
            statement.setString(parameterIndex++, status.name());
        }

        if (minDate != null) {
            statement.setDate(parameterIndex++, Date.valueOf(minDate));
        }
        if (maxDate != null) {
            statement.setDate(parameterIndex++, Date.valueOf(maxDate));
        }

        if (minTime != null) {
            statement.setTime(parameterIndex++, Time.valueOf(minTime));
        }
        if (maxTime != null) {
            statement.setTime(parameterIndex++, Time.valueOf(maxTime));
        }

        if (!rowsCounting) {
            statement.setInt(parameterIndex++, limit);
            statement.setInt(parameterIndex, offset);
        }


        return statement;
    }


    @Override
    public long getBooSearchResultCount(long masterId, Status status,
                                        LocalDate minDate, LocalDate maxDate,
                                        LocalTime minTime, LocalTime maxTime) {
        try{
            PreparedStatement statement = getPreparedAllSlotStatement(masterId, status, minDate, maxDate,
                    minTime, maxTime, Integer.MAX_VALUE, 0, true);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            String errorText = "Can't get slots count in search result. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override
    public List<Slot> getAllSlotParameterized(long masterId, Status status,
                                              LocalDate minDate, LocalDate maxDate,
                                              LocalTime minTime, LocalTime maxTime,
                                              int limit, int offset) {

        List<Slot> books = new ArrayList<>();

        try{
            PreparedStatement statement = getPreparedAllSlotStatement(masterId, status, minDate, maxDate,
                    minTime, maxTime, limit, offset, false);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                books.add(getSlotFromResultRow(rs));
            }

            rs.close();

        } catch (SQLException e) {
            String errorText = "Can't get books list from DB. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }

        return books;
    }

    @Override
    public void updateSlotStatus(long id, Status status) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement(DBQueries.UPDATE_SLOT_STATUS_QUERY);
            updateStatement.setString(1, status.name());
            updateStatement.setLong(2, id);

            updateStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't update slot status. User id: %s. Cause: %s", id, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override
    public Optional<Slot> get(long id) {
        Optional<Slot> resultOptional = Optional.empty();
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_SLOT_QUERY);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                resultOptional = Optional.of(getSlotFromResultRow(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            String errorText = String.format("Can't get an slot by id: %s. Cause: %s.", id, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
        return resultOptional;
    }

    @Override
    public List<Slot> getAll() {
        List<Slot> users = new ArrayList<>();

        try {
            PreparedStatement selectStatement = connection
                    .prepareStatement(DBQueries.GET_ALL_SLOT_QUERY);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                users.add(getSlotFromResultRow(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            String errorText = "Can't get slots list from DB. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }

        return users;
    }

    @Override
    public long save(Slot slot) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(DBQueries.SAVE_SLOT_QUERY, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setDate(1, Date.valueOf(slot.getDate()));
            insertStatement.setTime(2, Time.valueOf(slot.getStartTime()));
            insertStatement.setTime(3, Time.valueOf(slot.getEndTime()));
            insertStatement.setLong(4, slot.getMaster());
            insertStatement.setLong(5, slot.getUser());
            insertStatement.setString(6, slot.getStatus().name());
            insertStatement.setLong(7, slot.getProcedure());

            insertStatement.executeUpdate();

            return DBUtils.getIdFromStatement(insertStatement);

        } catch (SQLException e) {
            if (DBUtils.isTryingToInsertDuplicate(e)) {
                return -1;
            } else {
                String errorText = String.format("Can't save slot: %s. Cause: %s", slot, e.getMessage());
                LOG.error(errorText, e);
                throw new DaoException(errorText, e);
            }
        }
    }

    @Override
    public void update(Slot slot) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement(DBQueries.UPDATE_SLOT_INFO_QUERY);
            updateStatement.setDate(1, Date.valueOf(slot.getDate()));
            updateStatement.setTime(2, Time.valueOf(slot.getStartTime()));
            updateStatement.setTime(3, Time.valueOf(slot.getEndTime()));
            updateStatement.setLong(4, slot.getMaster());
            updateStatement.setLong(5, slot.getUser());
            updateStatement.setString(6, slot.getStatus().name());
            updateStatement.setLong(7, slot.getProcedure());
            updateStatement.setLong(8, slot.getId());

            updateStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't update slot: %s. Cause: %s", slot, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override
    public void delete(Slot slot) {
        try {
            PreparedStatement deleteStatement = connection
                    .prepareStatement(DBQueries.DELETE_SLOT_QUERY);
            deleteStatement.setLong(1, slot.getId());

            deleteStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't delete slot: %s. Cause: %s", slot, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * Creates an slot from given {@code ResultSet}
     *
     * @param resultSet - {@code ResultSet} with slot data
     * @return - slot was passed into
     * @throws SQLException if the columnLabels is not valid;
     *                      if a database access error occurs or result set is closed
     */
    Slot getSlotFromResultRow(ResultSet resultSet) throws SQLException {
        return Slot.builder()
                .id(resultSet.getLong("slot_id"))
                .date(resultSet.getObject("date", LocalDate.class))
                .startTime(resultSet.getObject("start_time", LocalTime.class))
                .endTime(resultSet.getObject("end_time", LocalTime.class))
                .master(resultSet.getLong("master"))
                .user(resultSet.getLong("user"))
                .status(Status.valueOf(resultSet.getString("status")))
                .procedure(resultSet.getLong("procedure"))
                .build();
    }
}
