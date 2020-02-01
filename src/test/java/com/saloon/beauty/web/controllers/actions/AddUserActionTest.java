package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.AddUserForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddUserActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletResources resources;

    @Mock
    UserService userService;

    private AddUserAction action;
    private AddUserForm form;
    private User user;

    @Before
    public void init() {
        action = spy(new AddUserAction());
        form = new AddUserForm();
        action.setUserService(userService);
        user = User.builder()
                .id(8L)
                .firstName("testFirst")
                .lastName("testLast")
                .phone("testPhone")
                .email("testMail")
                .password("testPassword")
                .role(Role.USER)
                .build();
        form.setFirstName("testFirst");
        form.setLastName("testLast");
        form.setPhone("testPhone");
        form.setEmail("testMail");
        form.setPassword("testPassword");
        form.setRole(Role.USER);
    }

    @Test
    public void createUserShouldSaveNewUser(){
        when(userService.createNewUser("testFirst", "testLast", "testMail", "testPhone", "testPassword", "USER")).thenReturn(Optional.of(user));
        Optional<User> optionalTestUser = action.createUser(form);
        verify(userService).createNewUser("testFirst", "testLast", "testMail", "testPhone", "testPassword", "USER");
        assertEquals(optionalTestUser.get(), user);
    }

    @Test
    public void executeShouldSetSuccessfulParameterToRequest(){
        when(action.createUser(form)).thenReturn(Optional.of(user));
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "registration.userCreated");
    }

    @Test
    public void executeShouldSetFailedParameterToRequest(){
        when(action.createUser(form)).thenReturn(Optional.empty());
        action.execute(request, response, form, resources);
        verify(request).setAttribute("actionResult", "registration.userNotCreate");
    }

    @Test
    public void executeShouldForwardToShowUserSearchPage() {
        when(resources.getForward("ShowUserSearchPage")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

}