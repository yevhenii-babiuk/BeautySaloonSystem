package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DBUtils;
import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.ProcedureDao;
import com.saloon.beauty.repository.entity.Feedback;
import com.saloon.beauty.repository.entity.Procedure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProcedureDaoImpl implements ProcedureDao {

    private static final Logger LOG = LogManager.getLogger(ProcedureDao.class);

    private Connection connection;

    public ProcedureDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Procedure> getProcedureByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_PROCEDURE_BY_NAME_QUERY);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(getProcedureFromResultRow(rs));
            } else {
                return Optional.empty();
            }


        } catch (SQLException e) {
            String errorText = String.format("Can't get an procedure by name: %s. Cause: %s.", name, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override
    public int getPriceByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_PROCEDURE_PRICE_BY_NAME_QUERY);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }


        } catch (SQLException e) {
            String errorText = String.format("Can't get an procedure price by name: %s. Cause: %s.", name, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override
    public String getDescriptionByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_PROCEDURE_DESCRIPTION_BY_NAME_QUERY);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }


        } catch (SQLException e) {
            String errorText = String.format("Can't get an procedure description by name: %s. Cause: %s.", name, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override
    public Optional<Procedure> get(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_PROCEDURE_QUERY);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(getProcedureFromResultRow(rs));
            } else {
                return Optional.empty();
            }


        } catch (SQLException e) {
            String errorText = String.format("Can't get an procedure by id: %s. Cause: %s.", id, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override
    public List<Procedure> getAll() {
        List<Procedure> procedures = new ArrayList<>();

        try {
            PreparedStatement selectStatement = connection
                    .prepareStatement(DBQueries.GET_ALL_PROCEDURES_QUERY);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                procedures.add(getProcedureFromResultRow(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            String errorText = "Can't get feedback list from DB. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }

        return procedures;
    }

    @Override
    public long save(Procedure procedure) {
        try {
            PreparedStatement saveStatement = connection
                    .prepareStatement(DBQueries.SAVE_PROCEDURE_QUERY, Statement.RETURN_GENERATED_KEYS);
            saveStatement.setString(1, procedure.getName());
            saveStatement.setString(2, procedure.getDescription());
            saveStatement.setInt(3,procedure.getPrice());

            saveStatement.executeUpdate();

            return DBUtils.getIdFromStatement(saveStatement);

        } catch (SQLException e) {
            if (DBUtils.isTryingToInsertDuplicate(e)) {
                return -1;
            } else {
                String errorText = String.format("Can't save procedure: %s. Cause: %s", procedure, e.getMessage());
                LOG.error(errorText, e);
                throw new DaoException(errorText, e);
            }
        }
    }

    @Override
    public void update(Procedure procedure) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement(DBQueries.UPDATE_PROCEDURE_INFO_QUERY);
            updateStatement.setString(1, procedure.getName());
            updateStatement.setString(2, procedure.getDescription());
            updateStatement.setInt(3,procedure.getPrice());
            updateStatement.setLong(4, procedure.getId());

            updateStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't update procedure: %s. Cause: %s", procedure, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    @Override

    public void delete(Procedure procedure) {
        try {
            PreparedStatement deleteStatement = connection
                    .prepareStatement(DBQueries.DELETE_PROCEDURE_QUERY);
            deleteStatement.setLong(1, procedure.getId());

            deleteStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't delete procedure: %s. Cause: %s", procedure, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * Creates an slots from given {@code ResultSet}
     *
     * @param resultSet - {@code ResultSet} with slots data
     * @return - slot was passed into
     * @throws SQLException if the columnLabels is not valid;
     *                      if a database access error occurs or result set is closed
     */
    Procedure getProcedureFromResultRow(ResultSet resultSet) throws SQLException {
        return Procedure.builder()
                .id(resultSet.getLong("procedure_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getInt("price"))
                .build();
    }
}
