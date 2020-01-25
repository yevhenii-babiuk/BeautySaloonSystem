package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.Procedure;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.web.controllers.PaginationHelper;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action for showing the procedures page
 */
public class ShowProcedurePageAction extends Action {

    private ProcedureService procedureService;

    /**
     * Finds paginated part of procedures
     * and sets {@code List} with searching
     * results as session attribute
     *
     * @param request   the request need to be processed
     * @param response  the response to user
     * @param form      - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path for forwarding to the slot search page
     * for showing the searching results
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        List<Procedure> proceduresList = getProceduresList(request, procedureService, paginationHelper);

        setRequestAttributes(request, proceduresList);
        addPaginationToRequest(request, procedureService, paginationHelper);
        paginationHelper.addParameterToPagination(request);

        return resources.getForward("ShowProceduresPage");
    }

    void setRequestAttributes(HttpServletRequest request, List<Procedure> procedureList) {

        request.setAttribute("procedures", procedureList);
    }


    /**
     * Asks to {@code ProcedureService} to search procedures.
     *
     * @return list with searching results
     */
    List<Procedure> getProceduresList(HttpServletRequest request, ProcedureService procedureService, PaginationHelper paginationHelper) {

        int recordsPerPage = paginationHelper.getRecordsPerPage();
        int previousRecordNumber = paginationHelper.getPreviousRecordNumber(request, recordsPerPage);

        return procedureService.getProcedureParametrized( recordsPerPage, previousRecordNumber);
    }

    /**
     * Adds pagination to request
     */
    void addPaginationToRequest(HttpServletRequest request, ProcedureService procedureService, PaginationHelper paginationHelper) {
        long recordsQuantity = procedureService.getProcedureSearchResultCount();
        paginationHelper.addPaginationToRequest(request, recordsQuantity);
    }


    public void setProcedureService(Service procedureService) {
        this.procedureService = (ProcedureService) procedureService;
    }
}