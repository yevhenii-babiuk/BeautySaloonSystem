package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.AddFeedbackForm;
import com.saloon.beauty.web.controllers.forms.SlotForm;
import com.saloon.beauty.web.controllers.forms.SlotUpdateForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Action for adding feedback to slot
 */
public class UpdateSlotAction extends Action {

    private SlotService slotService;

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
        long slotId = ((SlotUpdateForm)form).getSlotId();
        long masterId = ((SlotUpdateForm)form).getMasterId();
        long procedureId = ((SlotUpdateForm)form).getProcedure();
        LocalDate date = ((SlotUpdateForm)form).getDate();
        LocalTime startTime = ((SlotUpdateForm)form).getStartTime();
        LocalTime endTime = ((SlotUpdateForm)form).getEndTime();

        boolean updatingResult = slotService.updateSlot(slotId, masterId, procedureId, date, startTime, endTime);

        if (updatingResult) {
            request.setAttribute("actionResult", "slotManagement.updateSlot.updatingSuccessful");
        } else  {
            request.setAttribute("actionResult", "slotManagement.updateSlot.updatingFailed");
        }

        return resources.createRedirectPath(resources.getForward("ShowSlotSearchAdminPage"));

    }


    public void setSlotService(Service slotService) {
        this.slotService = (SlotService) slotService;
    }

}
