package com.saloon.beauty.repository;

import com.saloon.beauty.repository.dao.*;
import com.saloon.beauty.repository.dao.implementation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manages connections lifecycle, transactions and DAO creations
 */
public class DaoManager {

    private static final Logger LOG = LogManager.getLogger(DaoManager.class);

    private DataSource dataSource;

    private Connection connection;

    public DaoManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Executes command which needs transaction logic.
     * After work closes connection.
     *
     * @param command - command to be executed
     * @return - object returned by command if executing successful
     * or null if executing failed
     */
    public Object executeTransaction(DaoManagerCommand command) {

        Object result = null;

        try {
            getConnection();
            connection.setAutoCommit(false);

            try {
                result = command.execute(this);
                connection.commit();

            } catch (DaoException e) {
                connection.rollback();
            }
            return result;

        } catch (SQLException e) {
            LOG.error("SQLException occurred. Cause: " + e.getMessage(), e);
            return null;
        } finally {
            closeConnection();
        }
    }

    /**
     * Executes command which has no need in transaction logic.
     * After work closes connection.
     *
     * @param command - command to be executed
     * @return - object returned by command if executing successful
     * or null if executing failed
     */
    public Object executeAndClose(DaoManagerCommand command) {

        try {
            getConnection();

            return command.execute(this);

        } catch (SQLException e) {
            LOG.error("SQLException occurred. Cause: " + e.getMessage(), e);
            return null;
        } catch (DaoException e) {
            //Command executing is failed so there is nothing to return
            return null;
        } finally {
            closeConnection();
        }
    }

    /**
     * Closes manager's connection and handles a possible Exception
     */
    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOG.error("Can't close connection. Cause: " + e.getMessage(), e);
        }
    }

    protected Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    // Dao getters
    public FeedbackDao getFeedbackDao() throws SQLException {
        return new FeedbackDaoImpl(getConnection());
    }

    public ProcedureDao getProcedureDao() throws SQLException {
        return new ProcedureDaoImpl(getConnection());
    }

    public SlotDao getSlotDao() throws SQLException {
        return new SlotDaoImpl(getConnection());
    }

    public UserDao getUserDao () throws SQLException {
        return new UserDaoImpl(getConnection());
    }

    public SlotDtoDao getSlotDtoDao() throws SQLException {
        return new SlotDtoDaoImpl(getConnection());
    }

    /**
     * Command class for executing by DaoManager
     */
    @FunctionalInterface
    public interface DaoManagerCommand {
        Object execute(DaoManager manager) throws SQLException;
    }

}
