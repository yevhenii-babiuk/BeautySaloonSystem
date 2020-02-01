package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.PaginationHelper;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.SlotSearchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Action for searching slot for administrator
 */
public class SlotSearchAdminAction extends Action {

    private SlotService slotService;

    private UserService userService;

    private ProcedureService procedureService;

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

        long masterId = ((SlotSearchForm)form).getMasterId();
        Status status = ((SlotSearchForm)form).getStatus();
        long procedureId = ((SlotSearchForm)form).getProcedure();
        LocalDate minDate = ((SlotSearchForm)form).getMinDate();
        LocalDate maxDate = ((SlotSearchForm)form).getMaxDate();
        LocalTime minTime = ((SlotSearchForm)form).getMinStartTime();
        LocalTime maxTime = ((SlotSearchForm)form).getMaxStartTime();

        List<SlotDto> slotDtoList = getSlotsList(request, masterId, status, procedureId, minDate, maxDate,
                minTime, maxTime, slotService, paginationHelper);

        setRequestAttributes(request,masterId, status, procedureId, minDate, maxDate,
                minTime, maxTime, slotDtoList, paginationHelper);
        addPaginationToRequest(request, slotService, masterId, status, procedureId, minDate, maxDate,
                minTime, maxTime, paginationHelper);

        return resources.getForward("ShowSlotSearchAdminPage");
    }

    void setRequestAttributes(HttpServletRequest request, long masterId, Status status, long procedureId,
                              LocalDate minDate, LocalDate maxDate, LocalTime minTime, LocalTime maxTime,
                              List<SlotDto> slotDtoList, PaginationHelper paginationHelper) {

        request.getSession().setAttribute("masters", userService.getAllMasters());
        request.getSession().setAttribute("procedures", procedureService.getAllProcedure());
        request.setAttribute("masterId", masterId);
        request.setAttribute("status", status);
        request.setAttribute("procedureId", procedureId);
        request.setAttribute("minDate", minDate);
        request.setAttribute("maxDate", maxDate);
        request.setAttribute("minTime", minTime);
        request.setAttribute("maxTime", maxTime);
        request.setAttribute("slots", slotDtoList);
        paginationHelper.addParameterToPagination(request);

    }


    /**
     * Takes user's slot searching parameters and asks to
     * {@code SlotService} to search for slots.
     * @return list with searching results
     */
    List<SlotDto> getSlotsList(HttpServletRequest request, long masterId, Status status, long procedureId,
                               LocalDate minDate, LocalDate maxDate,
                               LocalTime minTime, LocalTime maxTime,
                               SlotService slotService, PaginationHelper paginationHelper) {

        int recordsPerPage = paginationHelper.getRecordsPerPage();
        int previousRecordNumber = paginationHelper.getPreviousRecordNumber(request, recordsPerPage);

        return slotService.findSlots(masterId, status, 0L, procedureId, minDate, maxDate,
                minTime, maxTime, false, recordsPerPage, previousRecordNumber);
    }

    /**
     * Adds pagination to request
     */
    void addPaginationToRequest(HttpServletRequest request, SlotService slotService,long masterId, Status status, long procedureId,
                                LocalDate minDate, LocalDate maxDate,
                                LocalTime minTime, LocalTime maxTime, PaginationHelper paginationHelper) {
        long recordsQuantity = slotService.getSlotSearchResultCount(masterId, status, 0L, procedureId, minDate, maxDate, minTime, maxTime, false);
        paginationHelper.addPaginationToRequest(request, recordsQuantity);
    }

    public void setSlotService(Service slotService) {
        this.slotService = (SlotService) slotService;
    }

    public void setProcedureService(Service slotService) {
        this.procedureService = (ProcedureService) slotService;
    }

    public void setUserService(Service slotService) {
        this.userService = (UserService) slotService;
    }
}
