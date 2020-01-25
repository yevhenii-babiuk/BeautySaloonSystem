package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.PaginationHelper;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.UserSearchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Action for searching slot for signing up
 */
public class UserSearchAction extends Action {

    private UserService userService;

    /**
     * Finds paginated part of users which fits to admin's search
     * parameters and sets {@code List} with searching
     * results as session attribute
     *
     * @param request   the request need to be processed
     * @param response  the response to user
     * @param form      - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path for forwarding to the user search page
     * for showing the searching results
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        Role role = ((UserSearchForm) form).getRole();
        String searchString = ((UserSearchForm) form).getSearchString();
        String email = ((UserSearchForm) form).getEmail();
        String phone = ((UserSearchForm) form).getPhone();

        List<User> userList = getUsersList(request, searchString, role, email, phone, userService, paginationHelper);

        setRequestAttributes(request, searchString, role, email, phone, userList, paginationHelper);
        addPaginationToRequest(request, userService, searchString, role, email, phone, paginationHelper);

        return resources.getForward("ShowUserSearchPage");
    }

    void setRequestAttributes(HttpServletRequest request, String searchString, Role role,
                              String email, String phone, List<User> userList, PaginationHelper paginationHelper) {

        request.setAttribute("searchString", searchString);
        request.setAttribute("role", role);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("users", userList);
        paginationHelper.addParameterToPagination(request);
    }


    /**
     * Takes user's user searching parameters and asks to
     * {@code UserService} to search for users.
     *
     * @return list with searching results
     */
    List<User> getUsersList(HttpServletRequest request, String searchString, Role role,
                            String email, String phone,
                            UserService userService, PaginationHelper paginationHelper) {

        int recordsPerPage = paginationHelper.getRecordsPerPage();
        int previousRecordNumber = paginationHelper.getPreviousRecordNumber(request, recordsPerPage);

        return userService.getUsersByNameAndSurname(searchString, role, email, phone, recordsPerPage, previousRecordNumber);
    }

    /**
     * Adds pagination to request
     */
    void addPaginationToRequest(HttpServletRequest request, UserService userService, String searchString, Role role,
                                String email, String phone, PaginationHelper paginationHelper) {
        long recordsQuantity = userService.getUserSearchResultCount(searchString, role, email, phone);
        paginationHelper.addPaginationToRequest(request, recordsQuantity);
    }

    public void setUserService(Service slotService) {
        this.userService = (UserService) slotService;
    }
}
