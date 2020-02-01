package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DBUtils;
import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.ProcedureDao;

import com.saloon.beauty.repository.entity.Procedure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Implementing of ProcedureDao for working with a SQL server
 */
public class ProcedureDaoImpl implements ProcedureDao {

    private static final Logger LOG = LogManager.getLogger(ProcedureDao.class);

    private Connection connection;

    public ProcedureDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getProcedureSearchResultCount() {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.COUNT_ALL_PROCEDURE_QUERY);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            String errorText = "Can't get procedures count in search result. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Procedure> getAllProcedureParametrized(int limit, int offset) {
        List<Procedure> procedures = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_PROCEDURE_PARAMETRIZED_QUERY);

            statement.setLong(1, limit);
            statement.setLong(2, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                procedures.add(getProcedureFromResultRow(rs));
            }

            rs.close();

        } catch (SQLException e) {
            String errorText = "Can't get procedures list from DB. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }

        return procedures;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public long save(Procedure procedure) {
        try {
            PreparedStatement saveStatement = connection
                    .prepareStatement(DBQueries.SAVE_PROCEDURE_QUERY, Statement.RETURN_GENERATED_KEYS);
            saveStatement.setString(1, procedure.getNameUkr());
            saveStatement.setString(2, procedure.getDescriptionUkr());
            saveStatement.setString(3, procedure.getNameEn());
            saveStatement.setString(4, procedure.getDescriptionEn());
            saveStatement.setString(5, procedure.getNameRus());
            saveStatement.setString(6, procedure.getDescriptionRus());
            saveStatement.setInt(7,procedure.getPrice());

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Procedure procedure) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement(DBQueries.UPDATE_PROCEDURE_INFO_QUERY);
            updateStatement.setString(1, procedure.getNameUkr());
            updateStatement.setString(2, procedure.getDescriptionUkr());
            updateStatement.setString(3, procedure.getNameEn());
            updateStatement.setString(4, procedure.getDescriptionEn());
            updateStatement.setString(5, procedure.getNameRus());
            updateStatement.setString(6, procedure.getDescriptionRus());
            updateStatement.setInt(7,procedure.getPrice());
            updateStatement.setLong(8, procedure.getId());

            updateStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't update procedure: %s. Cause: %s", procedure, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
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
                .nameUkr(resultSet.getString("name_ukr"))
                .descriptionUkr(resultSet.getString("description_ukr"))
                .nameEn(resultSet.getString("name_en"))
                .descriptionEn(resultSet.getString("description_en"))
                .nameRus(resultSet.getString("name_rus"))
                .descriptionRus(resultSet.getString("description_rus"))
                .price(resultSet.getInt("price"))
                .build();
    }
}
