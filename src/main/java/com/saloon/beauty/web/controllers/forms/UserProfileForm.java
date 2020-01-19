package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains html-form data for showing user profile
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileForm extends ActionForm {

    private long userId;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(HttpServletRequest request) {
        userId = ((User)request.getSession().getAttribute("loggedInUser")).getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate() {
        return new ActionErrors();
    }


}
