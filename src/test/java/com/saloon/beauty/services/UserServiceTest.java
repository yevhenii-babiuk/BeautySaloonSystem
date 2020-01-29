package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserService mockService;

    @Mock
    DaoManager mockDaoManager;

    @Mock
    UserDao mockUserDao;

    @Mock
    User mockUser;

    @Before
    public void initSetUp() throws SQLException {
        mockService = spy(new UserService(new DaoManagerFactory()));
        when(mockDaoManager.getUserDao()).thenReturn(mockUserDao);
    }

    @Test
    public void createNewUserCommandShouldSaveUserAndReturnTrue() throws SQLException {
        final long TEST_USER_ID = 3L;
        when(mockUserDao.save(any(User.class))).thenReturn(TEST_USER_ID);

        boolean result = mockService.createNewUserCommand(mockDaoManager, mockUser);
        verify(mockUserDao).save(mockUser);
        assertTrue(result);
    }

    @Test
    public void createNewUserCommandShouldSaveUserAndReturnFalse() throws SQLException {
        final long TEST_USER_ID = -1L;
        when(mockUserDao.save(any(User.class))).thenReturn(TEST_USER_ID);

        boolean result = mockService.createNewUserCommand(mockDaoManager, mockUser);
        verify(mockUserDao).save(mockUser);
        assertFalse(result);
    }

    @Test
    public void updateUserDataCommandShouldUpdateUserAndReturnTrue() throws SQLException{
        when(mockUserDao.getUserByEmailAndPassword("test@test", "Hello")).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getEmail()).thenReturn("test@test");
        doNothing().when(mockUserDao).update(any(User.class));
        String oldPass = "Hello";
        String newPass = "World";

        boolean result = mockService.updateUserDataCommand(mockDaoManager, mockUser, newPass, oldPass);

        verify(mockUserDao).update(mockUser);
        assertTrue(result);
    }

    @Test
    public void updateUserDataCommandShouldUpdateUserAndReturnFalse() throws SQLException{
        when(mockUserDao.getUserByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.empty());
        when(mockUser.getEmail()).thenReturn("test@test");
        doNothing().when(mockUserDao).update(any(User.class));
        String oldPass = "Hello";
        String newPass = "World";

        boolean result = mockService.updateUserDataCommand(mockDaoManager, mockUser, newPass, oldPass);

        assertFalse(result);
    }
    @Test
    public void getAllMastersCommandShouldReturnListOfTwoMaster() throws SQLException {
        List<User> masters = List.of(User.builder().id(1L).build(), User.builder().id(2L).build());
        when(mockUserDao.getUserByRole(Role.MASTER)).thenReturn(masters);

        List<User> result = mockService.getAllMastersCommand(mockDaoManager);

        assertEquals(masters, result);
    }

    @Test
    public void testHashPassword(){

        UserService service = new UserService(new DaoManagerFactory());

        String password = "Hello";
        String hashedPassword = service.hashPassword(password);
        assertNotEquals("Password and hashed password must not be equal", password, hashedPassword);

        String hashedPassword2 = service.hashPassword(password);
        assertEquals("hashPassword method must return equals hashes for equals passwords", hashedPassword, hashedPassword2);

        String anotherPassword = "World";
        String anotherHash = service.hashPassword(anotherPassword);
        assertNotEquals("hashPassword method must return different hashes for different passwords", hashedPassword, anotherHash);
    }
}