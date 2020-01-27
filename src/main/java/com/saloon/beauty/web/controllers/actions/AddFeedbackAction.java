package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.FeedbackService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.AddFeedbackForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action for adding feedback to slot
 */
public class AddFeedbackAction extends Action {

    private FeedbackService feedbackService;

    /**
     * Stores adding feedback by {@code FeedbackService} and sets result
     * of this operation in a request
     *
     * @param request   the request need to be processed
     * @param response  the response to user
     * @param form      - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path to the {@code ShowSignedUpSlotsAction}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {
        long slotId = ((AddFeedbackForm) form).getSlotId();

        String feedbackText = ((AddFeedbackForm) form).getFeedbackText();

        long updatingResult = feedbackService.addFeedbackToSlot(slotId, feedbackText);

        setUpdatingResultInRequest(request, updatingResult);

        return resources.createRedirectPath(resources.getForward("ShowSignedUpSlotsAction"));

    }

    /**
     * Sets results of adding operation in a request
     *
     * @param request        for storing results
     * @param addingResult - result need to be stored
     */
    private void setUpdatingResultInRequest(HttpServletRequest request, long addingResult) {
        if (addingResult > 0) {
            request.setAttribute("actionResult", "feedbackManagement.addFeedback.addingSuccessful");
        } else if (addingResult == -2) {
            request.setAttribute("actionResult", "feedbackManagement.addFeedback.slotNotExist");
        } else if (addingResult == -1) {
            request.setAttribute("actionResult", "feedbackManagement.addFeedback.feedbackAlreadyExist");
        } else if (addingResult == 0) {
            request.setAttribute("actionResult", "feedbackManagement.addFeedback.slotNotEnded");
        } else {
            request.setAttribute("actionResult", "feedbackManagement.addFeedback.addingFailed");
        }
    }

    public void setFeedbackService(Service feedbackService) {
        this.feedbackService = (FeedbackService) feedbackService;
    }

}
