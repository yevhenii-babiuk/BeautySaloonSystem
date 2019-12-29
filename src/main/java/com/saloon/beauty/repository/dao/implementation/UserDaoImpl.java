package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DBUtils;
import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_USER_BY_EMAIL_AND_PASSWORD_QUERY);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(getUserFromResultRow(rs));
            } else {
                return Optional.empty();
            }


        } catch (SQLException e) {
            String errorText = String.format("Can't get an user by email & password. " +
                    "Email: %s. Password: %s. Cause: %s.", email, password, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Role> getRoleByUserId(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_USER_ROLE_BY_USER_ID_QUERY);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Role.valueOf(resultSet.getString("role")));
            } else {
                return Optional.empty();
            }


        } catch (SQLException e) {
            String errorText = String.format("Can't get user role by id: %s. Cause: %s", id, e.getMessage() );
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> get(long id) {
        Optional<User> resultOptional = Optional.empty();
        try {
            PreparedStatement getUserStatement = connection
                    .prepareStatement(DBQueries.GET_USER_QUERY);
            getUserStatement.setLong(1, id);

            ResultSet resultSet = getUserStatement.executeQuery();

            if (resultSet.next()) {
                resultOptional = Optional.of(getUserFromResultRow(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            String errorText = String.format("Can't get user by id: %s. Cause: %s", id, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
        return resultOptional;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement selectStatement = connection
                    .prepareStatement(DBQueries.GET_ALL_USERS_QUERY);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                users.add(getUserFromResultRow(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            String errorText = "Can't get users list from DB. Cause: " + e.getMessage();
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }

        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long save(User user) {

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(DBQueries.SAVE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getRole().name());

            preparedStatement.executeUpdate();

            return DBUtils.getIdFromStatement(preparedStatement);

        } catch (SQLException e) {
            if (DBUtils.isTryingToInsertDuplicate(e)) {
                return -1;
            } else {
                String errorText = String.format("Can't save user: %s. Cause: %s", user, e.getMessage());
                LOG.error(errorText, e);
                throw new DaoException(errorText, e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(User user) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement(DBQueries.UPDATE_USER_INFO_QUERY);
            updateStatement.setString(1, user.getEmail());
            updateStatement.setString(2, user.getPhone());
            updateStatement.setString(3, user.getFirstName());
            updateStatement.setString(4, user.getLastName());
            updateStatement.setLong(5, user.getId());

            updateStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't update user: %s. Cause: %s", user, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(User user) {
        try {
            PreparedStatement deleteStatement = connection
                    .prepareStatement(DBQueries.DELETE_USER_QUERY);
            deleteStatement.setLong(1, user.getId());

            deleteStatement.execute();

        } catch (SQLException e) {
            String errorText = String.format("Can't delete user: %s. Cause: %s", user, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
    }

    /**
     * Creates an user(without password field) from given {@code ResultSet}
     *
     * @param rs - {@code ResultSet} with users data
     * @return - user whose email & password was passed into
     * @throws SQLException if the columnLabels is not valid;
     *                      if a database access error occurs or result set is closed
     */
    User getUserFromResultRow(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
    }
}
