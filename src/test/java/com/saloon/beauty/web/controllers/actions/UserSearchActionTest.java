package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.PaginationHelper;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.UserSearchForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserSearchActionTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    ServletResources resources;

    @Mock
    UserService service;

    @Mock
    PaginationHelper helper;

    private UserSearchForm form;
    private UserSearchAction action;

    private String TEST_STRING = "test name";
    private String TEST_EMAIL = "test email";
    private String TEST_PHONE = "+38094446862";

    @Before
    public void init() {
        action = spy(new UserSearchAction());
        form = new UserSearchForm();
        action.setUserService(service);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void executeShouldForwardToShowUserSearchPage() {
        when(resources.getForward("ShowUserSearchPage")).thenReturn("testPath");
        String returnedPath = action.execute(request, response, form, resources);
        assertEquals("testPath", returnedPath);
    }

    @Test
    public void executeShouldProperlyCallLocalMethods(){
        List<User> list = Collections.singletonList(User.builder().id(9L).build());
        form.setSearchString(TEST_STRING);
        form.setEmail(TEST_EMAIL);
        form.setPhone(TEST_PHONE);
        form.setRole(Role.USER);

        doReturn(list).when(action).getUsersList(eq(request), eq(TEST_STRING),
                any(Role.class), eq(TEST_EMAIL), eq(TEST_PHONE), eq(service), any(PaginationHelper.class));
        doNothing().when(action).setRequestAttributes(eq(request), eq(TEST_STRING),
                any(Role.class), eq(TEST_EMAIL), eq(TEST_PHONE), anyList(), any(PaginationHelper.class));
        doNothing().when(action).addPaginationToRequest(eq(request), eq(service), eq(TEST_STRING),
                any(Role.class), eq(TEST_EMAIL), eq(TEST_PHONE), any(PaginationHelper.class));

        action.execute(request, response, form, resources);

        verify(action).setRequestAttributes(eq(request), eq(TEST_STRING),
                any(Role.class), eq(TEST_EMAIL), eq(TEST_PHONE), anyList(), any(PaginationHelper.class));
        verify(action).addPaginationToRequest(eq(request), eq(service), eq(TEST_STRING),
                any(Role.class), eq(TEST_EMAIL), eq(TEST_PHONE), any(PaginationHelper.class));
    }

    @Test
    public void setRequestAttributesShouldProperlySetAttributes() {
        action.setRequestAttributes(request, TEST_STRING, Role.USER,
                TEST_EMAIL, TEST_PHONE,  Collections.emptyList(), helper);

        verify(request).setAttribute("searchString", TEST_STRING);
        verify(request).setAttribute("role", Role.USER);
        verify(request).setAttribute("email", TEST_EMAIL);
        verify(request).setAttribute("phone", TEST_PHONE);
        verify(request).setAttribute("users", Collections.emptyList());
    }

    @Test
    public void getUsersListShouldProperlyGetList() {
        when(helper.getRecordsPerPage()).thenReturn(10);
        when(helper.getPreviousRecordNumber(request, 10)).thenReturn(20);

        action.getUsersList(request, TEST_STRING, Role.USER, TEST_EMAIL, TEST_PHONE, service, helper);
        verify(service).getUsersByNameAndSurname(TEST_STRING, Role.USER, TEST_EMAIL, TEST_PHONE, 10, 20);
    }

    @Test
    public void addPaginationToRequestShouldProperlyAddPagination() {
        when(service.getUserSearchResultCount(TEST_STRING, Role.USER, TEST_EMAIL, TEST_PHONE)).thenReturn(43L);
        action.addPaginationToRequest(request, service, TEST_STRING, Role.USER, TEST_EMAIL, TEST_PHONE, helper);
        verify(helper).addPaginationToRequest(request, 43L);
    }

}