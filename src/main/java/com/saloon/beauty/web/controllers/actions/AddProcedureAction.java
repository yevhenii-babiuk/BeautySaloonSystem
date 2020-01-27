package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.FeedbackService;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.AddFeedbackForm;
import com.saloon.beauty.web.controllers.forms.AddProcedureForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action for adding procedure
 */
public class AddProcedureAction extends Action {

    private ProcedureService procedureService;

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
        String nameEnglish = ((AddProcedureForm) form).getNameEnglish();
        String nameUkrainian = ((AddProcedureForm) form).getNameUkrainian();
        String nameRussian = ((AddProcedureForm) form).getNameRussian();
        String descriptionEnglish = ((AddProcedureForm) form).getDescriptionEnglish();
        String descriptionUkrainian = ((AddProcedureForm) form).getDescriptionUkrainian();
        String descriptionRussian = ((AddProcedureForm) form).getDescriptionRussian();
        int price = ((AddProcedureForm) form).getPrice();

        long updatingResult = procedureService.addNewProcedure(nameUkrainian, descriptionUkrainian,
                nameEnglish, descriptionEnglish,
                nameRussian, descriptionRussian,
                price);

        setUpdatingResultInRequest(request, updatingResult);

        return resources.createRedirectPath(resources.getForward("ShowProceduresAction"));

    }

    /**
     * Sets results of adding operation in a request
     *
     * @param request        for storing results
     * @param addingResult - result need to be stored
     */
    private void setUpdatingResultInRequest(HttpServletRequest request, long addingResult) {
        if (addingResult > 0) {
            request.setAttribute("actionResult", "procedureManagement.addProcedure.addingSuccessful");
        } else {
            request.setAttribute("actionResult", "procedureManagement.addProcedure.addingFailed");
        }
    }

    public void setProcedureService(Service procedureService) {
        this.procedureService = (ProcedureService) procedureService;
    }

}
