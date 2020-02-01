package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Dao for User entity
 */
public interface UserDao extends Dao<User> {

    /**
     * Gets an {@code Optional} with user by its email & password
     * @param email - email of a target user
     * @param password - password of a target use
     * @return user if it exist
     */
    Optional<User> getUserByEmailAndPassword(String email, String password);


    /**
     * Gets all user by such role
     * @param role - role of target users
     * @return {@code List} of users
     */
    List<User> getUserByRole(Role role);

    /**
     * Finds all users in a DB according to search parameters(with all ar with any of them)
     * @param searchString - string that contains name or surname of searching user
     * @param role - user`s role
     * @param email - user`s email
     * @param phone - user`s phone
     * @param limit the number of user
     * @param offset the number of user
     * @return - list with all users correspond to the given criteria
     */
    List<User> getUserParameterized(String searchString, Role role, String email, String phone, int limit, int offset);

    /**
     * Counts amount of all users in a DB according to search parameters(with all ar with any of them)
     * @param searchString - string that contains name or surname of searching user
     * @param role - user`s role
     * @param email - user`s email
     * @param phone - user`s phone
     * @return amount of all users correspond to the given criteria
     */
    long getUserSearchResultCount(String searchString, Role role, String email, String phone);

}
