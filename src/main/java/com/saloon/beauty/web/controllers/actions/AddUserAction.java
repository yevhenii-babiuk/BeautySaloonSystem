package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.ActionErrors;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.AddUserForm;
import com.saloon.beauty.web.controllers.forms.UserRegistrationForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Action for adding user
 */
public class AddUserAction extends Action {

    private UserService userService;

    /**
     * Uses {@code UserService} for adding a new user
     * @param request the request need to be processed
     * @param response the response to user
     * @param form - form need to be processed by this action
     * @param resources - servlet's resources
     * @return a path to {@code ShowUserSearchPage}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response,
                          ActionForm form, ServletResources resources) {

        Optional<User> userOptional = createUser(form);

        if (userOptional.isPresent()) {
            request.setAttribute("actionResult", "registration.userCreated");
        } else {
            request.setAttribute("actionResult", "registration.userNotCreate");
        }

        return resources.getForward("ShowUserSearchPage");
    }

     /**
     * Creates an user from form data
     */
    Optional<User> createUser(ActionForm form) {
        AddUserForm userForm = (AddUserForm) form;
        return userService.createNewUser(
                userForm.getFirstName(),
                userForm.getLastName(),
                userForm.getEmail(),
                userForm.getPhone(),
                userForm.getPassword(),
                userForm.getRole().name());
    }

    public void setUserService(Service userService) {
        this.userService = (UserService) userService;
    }
}
