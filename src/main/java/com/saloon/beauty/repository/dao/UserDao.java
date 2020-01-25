package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.repository.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> getUserByEmailAndPassword(String email, String password);

    Optional<Role> getRoleByUserId(long id);

    List<User> getUserByRole(Role role);

    List<User> getUserParameterized(String searchString, Role role, String email, String phone, int limit, int offset);

    long getUserSearchResultCount(String searchString, Role role, String email, String phone);

}
