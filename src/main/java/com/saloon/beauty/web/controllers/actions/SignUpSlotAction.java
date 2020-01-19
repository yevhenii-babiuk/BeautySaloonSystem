package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.SignUpSlotForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action for sign up to slot by an user
 */
public class SignUpSlotAction extends Action {

    private SlotService slotService;

    /**
     * Takes target slot's ID from {@code form} and makes
     * this slot booked by user stored in current
     * session
     * @param request {@inheritDoc}
     * @param response {@inheritDoc}
     * @param form - {@inheritDoc}
     * @param resources - {@inheritDoc}
     * @return path for forwarding to the show slots page with
     * booking result info
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        if (signUpToSlot(request, form)) {
            request.setAttribute("actionResult", "slotSearch.result.successfulSignUp");
        } else {
            request.setAttribute("actionResult", "slotSearch.result.failedSignUp");
        }

        return resources.getForward("ShowSignedUpSlotsAction");
    }

    /**
     * Tries to sign up to target slot for current user
     * @param request - the request with user info stored in session
     * @param form - form with target slot's ID
     * @return boolean result of making application
     */
    boolean signUpToSlot(HttpServletRequest request, ActionForm form) {

        long slotId = ((SignUpSlotForm) form).getSlotId();
        long userId = ((User) request.getSession().getAttribute("loggedInUser")).getId();

        return slotService.updateSlotStatus(slotId, userId, true);
    }

    public void setSlotService(Service slotService) {
        this.slotService = (SlotService) slotService;
    }
}
