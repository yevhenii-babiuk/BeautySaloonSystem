package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service class which has methods bound with user operations
 * and DAO
 */
public class UserService extends Service {

    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private DaoManagerFactory daoManagerFactory;

    UserService(DaoManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }

    /**
     * Creates(saves) a new user.
     *
     * @param firstName - the user's first name
     * @param lastName  - the user's last name
     * @param email     - the user's e-mail
     * @param phone     - the user's phone number
     * @param password  - the user's password
     * @param role - the user's role
     * @return an {@code Optional} with created user if saving
     * was successful or an empty {@code Optional} if it wasn't
     */
    public Optional<User> createNewUser(String firstName,
                                        String lastName,
                                        String email,
                                        String phone,
                                        String password,
                                        String role) {

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();

        user.setRole(Role.valueOf(role));
        user.setPassword(hashPassword(password));

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeTransaction(manager -> createNewUserCommand(manager, user));

        if (checkAndCastExecutingResult(executingResult)) {
            //Erasing unnecessary for caller password
            user.setPassword("");
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }


    /**
     * Gives an {@code Optional} of user with given combination of email & password
     *
     * @param email    - an user's email
     * @param password - a user's password
     * @return an {@code Optional} of target user or an empty {@code Optional}
     * if there is no user with given combination of email & password
     * of if there are exceptions during user getting process
     */
    public Optional<User> getUserByLoginInfo(String email, String password) {
        //The passwords stored in their hashed version so we need
        // to get hash of the password first
        String hashedPassword = hashPassword(password);

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager ->
                manager.getUserDao().getUserByEmailAndPassword(email, hashedPassword));

        return checkAndCastObjectToOptional(executingResult);
    }

    /**
     * Gives an {@code Optional} of user with given id
     *
     * @param userId - ID of target user
     * @return an {@code Optional} of target user or an empty {@code Optional}
     * if there are exceptions during user getting process
     */
    public Optional<User> getUserById(long userId) {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager -> manager.getUserDao().get(userId));

        return checkAndCastObjectToOptional(executingResult);
    }

    /**
     * Creates(saves) a new user.
     *
     * @param firstName   - the user's first name
     * @param lastName    - the user's last name
     * @param email       - the user's e-mail
     * @param phone       - the user's phone number
     * @param oldPassword - the user's old password
     * @param newPassword - the user's new password
     * @return an {@code Optional} with created user if saving
     * was successful or an empty {@code Optional} if it wasn't
     */
    public boolean updateUserData(String email,
                                  String oldPassword,
                                  String newPassword,
                                  String phone,
                                  String firstName,
                                  String lastName
    ) {


        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();

        String hashOldPassword = hashPassword(oldPassword);
        String hashNewPassword = hashPassword(newPassword);

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> updateUserDataCommand(manager, user, hashNewPassword, hashOldPassword));

        return checkAndCastExecutingResult(executionResult);
    }

    public List<User> getAllMasters(){
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(this::getAllMastersCommand);

        return checkAndCastObjectToList(executionResult);
    }

    public List<User> getUsersByNameAndSurname(String searchString, Role role, String email, String phone,
                                               int recordsQuantity, int previousRecordNumber){
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult=daoManager.executeAndClose(manager -> getUsersByNameAndSurnameCommand(manager, searchString, role, email, phone,
                recordsQuantity, previousRecordNumber));

        return checkAndCastObjectToList(executionResult);
    }

    public long getUserSearchResultCount(String searchString, Role role, String email, String phone){
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> manager.getUserDao().
                getUserSearchResultCount(searchString,role, email, phone));

        return checkAndCastObjectToLong(executionResult);
    }



    //Commands which is needed to be executed in corresponding public service methods
    synchronized boolean updateUserDataCommand(DaoManager manager, User user, String newPass, String oldPass) throws SQLException {

        UserDao userDao = manager.getUserDao();
        Optional<User> findingUser = userDao.getUserByEmailAndPassword(user.getEmail(), oldPass);
        User updateUser = null;
        if (findingUser.isPresent()) {
            updateUser = findingUser.get();
        } else {
            return  EXECUTING_FAILED;
        }
        user.setId(updateUser.getId());
        user.setPassword(newPass);
        user.setRole(updateUser.getRole());

        userDao.update(user);

        return EXECUTING_SUCCESSFUL;
    }

    boolean createNewUserCommand(DaoManager manager, User user) throws SQLException {

        UserDao userDao = manager.getUserDao();
        long id = userDao.save(user);
        if (id > 0) {
            user.setId(id);
            return EXECUTING_SUCCESSFUL;
        } else {
            return EXECUTING_FAILED;
        }
    }

    List<User> getAllMastersCommand(DaoManager manager) throws SQLException {
        return manager.getUserDao().getUserByRole(Role.MASTER);
    }

    List<User> getUsersByNameAndSurnameCommand(DaoManager manager, String searchString, Role role, String email, String phone,
                                               int recordsQuantity, int previousRecordNumber) throws SQLException {
        return manager.getUserDao().getUserParameterized(searchString, role, email, phone, recordsQuantity, previousRecordNumber);
    }


    /**
     * Makes the hashed password for storing hash of the password instead
     * of it's raw version
     *
     * @param passwordToHash - password which is need to be hashed
     * @return - hashed version of password
     */
    String hashPassword(String passwordToHash) {

        final String salt = "nox#!9Z7";

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            LOG.error("Password hasher can't find hash algorithm", e);
        }


        byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8);
    }

}
