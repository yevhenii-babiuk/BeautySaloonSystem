package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.UpdateProcedureForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action for updating procedure
 */
public class UpdateProcedureAction extends Action {

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

        long procedureId = ((UpdateProcedureForm)form).getProcedureId();
        String nameEnglish = ((UpdateProcedureForm)form).getNameEnglish();
        String nameUkrainian = ((UpdateProcedureForm)form).getNameUkrainian();
        String nameRussian = ((UpdateProcedureForm)form).getNameRussian();
        String descriptionRussian = ((UpdateProcedureForm)form).getDescriptionRussian();
        String descriptionUkrainian = ((UpdateProcedureForm)form).getDescriptionUkrainian();
        String descriptionEnglish = ((UpdateProcedureForm)form).getDescriptionEnglish();
        int price = ((UpdateProcedureForm)form).getPrice();

        boolean updatingResult = procedureService.updateProcedure(procedureId, nameUkrainian, descriptionUkrainian, nameEnglish,
                descriptionEnglish, nameRussian, descriptionRussian, price);

        if (updatingResult) {
            request.setAttribute("actionResult", "procedureManagement.updateProcedure.updatingSuccessful");
        } else  {
            request.setAttribute("actionResult", "procedureManagement.updateProcedure.updatingFailed");
        }

        return resources.createRedirectPath(resources.getForward("ShowProceduresAction"));

    }


    public void setProcedureService(Service procedureService) {
        this.procedureService = (ProcedureService) procedureService;
    }

}
