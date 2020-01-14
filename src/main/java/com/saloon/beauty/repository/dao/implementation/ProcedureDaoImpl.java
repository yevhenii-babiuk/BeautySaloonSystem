package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DBUtils;
import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.ProcedureDao;

import com.saloon.beauty.repository.entity.Procedure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

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
            statement.setString(2, name);
            statement.setString(3, name);

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
            statement.setString(2, name);
            statement.setString(3, name);

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
    public Map<String, String> getDescriptionByName(String name) {
        Map<String, String> descriptions = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_PROCEDURE_DESCRIPTION_BY_NAME_QUERY);
            statement.setString(1, name);
            statement.setString(2, name);
            statement.setString(3, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                descriptions.put("UKR", resultSet.getString("description_ukr"));
                descriptions.put("RUS", resultSet.getString("description_rus"));
                descriptions.put("EN", resultSet.getString("description_en"));
            }

            resultSet.close();


        } catch (SQLException e) {
            String errorText = String.format("Can't get an procedure description by name: %s. Cause: %s.", name, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
        return descriptions;
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
