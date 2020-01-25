package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.entity.Procedure;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.ProcedureIdForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Action for showing the procedure updating page
 */
public class ShowUpdateProcedurePageAction extends Action {

    private ProcedureService procedureService;

    /**
     * Attaches to the session attributes
     * procedure data and shows the procedure updating page
     * @param request the request need to be processed
     * @param response the response to user
     * @param form - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path for forwarding to procedures page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        Optional<Procedure> optionalProcedure = getProcedureById(request, form);

        if (optionalProcedure.isPresent()){
            Procedure procedure = optionalProcedure.get();
            request.setAttribute("procedure", procedure);
        }else if(optionalProcedure.isEmpty()){
            request.setAttribute("actionResult", "procedureManagement.procedure.error.notExist");
            return resources.getForward("ShowProceduresAction");
        }

        return resources.getForward("ShowProcedureUpdatePage");
    }

    private Optional<Procedure> getProcedureById(HttpServletRequest request, ActionForm form) {

        long id = ((ProcedureIdForm)form).getProcedureId();

        return procedureService.getProcedureById(id);
    }


    public void setProcedureService(Service procedureService) {
        this.procedureService = (ProcedureService) procedureService;
    }

}
