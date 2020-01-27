package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.SlotForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Action for adding slot
 */
public class AddSlotAction extends Action {

    private SlotService slotService;

    /**
     * Finds paginated part of slots which fits to user's slot search
     * parameters and sets {@code List} with searching
     * results as session attribute
     * @param request the request need to be processed
     * @param response the response to user
     * @param form - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path for forwarding to the slot search page
     * for showing the searching results
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        long masterId = ((SlotForm)form).getMasterId();
        long procedureId = ((SlotForm)form).getProcedure();
        LocalDate date = ((SlotForm)form).getDate();
        LocalTime startTime = ((SlotForm)form).getStartTime();
        LocalTime endTime = ((SlotForm)form).getEndTime();

        long updatingResult = slotService.addNewSlot(date, startTime, endTime, masterId, 0L, procedureId);

        setUpdatingResultInRequest(request, updatingResult);

        return resources.createRedirectPath(resources.getForward("ShowSlotSearchAdminPage"));
    }


    /**
     * Sets results of adding operation in a request
     *
     * @param request        for storing results
     * @param addingResult - result need to be stored
     */
    private void setUpdatingResultInRequest(HttpServletRequest request, long addingResult) {
        if (addingResult > 0) {
            request.setAttribute("actionResult", "slotManagement.addSlot.addingSuccessful");
        } else  {
            request.setAttribute("actionResult", "slotManagement.addSlot.addingFailed");
        }
    }

    public void setSlotService(Service slotService) {
        this.slotService = (SlotService) slotService;
    }
}
