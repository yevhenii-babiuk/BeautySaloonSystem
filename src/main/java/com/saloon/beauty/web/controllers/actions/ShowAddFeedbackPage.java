package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.FeedbackService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.AddFeedbackForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Action for adding feedback to slot
 */
public class ShowAddFeedbackPage extends Action {

    private FeedbackService feedbackService;

    /**
     * Creates {@code AddFeedbackForm} object and fills it
     * with feedback information for its further adding
     * @param request the request need to be processed
     * @param response the response to user
     * @param form - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path to the user`s slots page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        long slotId = ((AddFeedbackForm) form).getSlotId();

        request.setAttribute("slotId", slotId);

        return resources.getForward("ShowAddingFeedbackPage");
    }

    public void setFeedbackService(Service feedbackService) {
        this.feedbackService = (FeedbackService) feedbackService;
    }

}
