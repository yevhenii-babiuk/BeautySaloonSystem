package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.web.controllers.PaginationHelper;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action for showing signed up slot by user
 */
public class ShowSignedUpSlotsAction extends Action {

    private SlotService slotService;

    /**
     * Gets all sign up slot of user and adds them to request
     * @param request the request need to be processed
     * @param response the response to user
     * @param form - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path to the signed up slots by user page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        long userId = ((User) request.getSession().getAttribute("loggedInUser")).getId();
        List<SlotDto> slots = getUserSlots(userId, request, slotService, paginationHelper);
        request.setAttribute("slots", slots);

        addPaginationToRequest(userId, request, paginationHelper);
        paginationHelper.addParameterToPagination(request);

        return resources.getForward("ShowSignedUpSlotsPage");
    }

    /**
     * Gives a {@code List} with paginated part of all slots
     */
    List<SlotDto> getUserSlots(long userId, HttpServletRequest request, SlotService slotService, PaginationHelper paginationHelper) {

        int recordsPerPage = paginationHelper.getRecordsPerPage();
        int previousRecordNumber = paginationHelper.getPreviousRecordNumber(request, recordsPerPage);

        return slotService
                .findSlots(0L, Status.BOOKED, userId, 0L,
                        null, null, null, null, recordsPerPage, previousRecordNumber);
    }

    /**
     * Adds pagination to request
     */
    void addPaginationToRequest(long userId, HttpServletRequest request, PaginationHelper paginationHelper) {
        long recordsQuantity = slotService.getSlotSearchResultCount(0L, Status.BOOKED, userId, 0L,
                null, null, null, null);
        paginationHelper.addPaginationToRequest(request, recordsQuantity);
    }

    public void setSlotService(Service slotService) {
        this.slotService = (SlotService) slotService;
    }

}
