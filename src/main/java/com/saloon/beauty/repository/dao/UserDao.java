package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> getUserByEmailAndPassword(String email, String password);

    Optional<Role> getRoleByUserId(long id);

    List<User> getUserByRole(Role role);

}
