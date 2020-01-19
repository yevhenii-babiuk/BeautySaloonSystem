package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action for showing the feedback search page
 */
public class ShowFeedbackSearchPageAction extends Action {

    private ProcedureService procedureService;
    private UserService userService;

    /**
     * Attaches to the session attributes with masters
     * and procedures lists and shows the slot searching page
     * @param request the request need to be processed
     * @param response the response to user
     * @param form - form need to be processed by this action
     * @param resources - servlet's resources
     * @return path for forwarding to slot searching page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ServletResources resources) {

        setMastersAttribute(request);
        setProceduresAttribute(request);

        return resources.getForward("ShowFeedbackSearchPage");
    }


    private void setMastersAttribute(HttpServletRequest request) {
        request.getSession().setAttribute("masters", userService.getAllMasters());
    }


    private void setProceduresAttribute(HttpServletRequest request) {
        request.getSession().setAttribute("procedures", procedureService.getAllProcedure());
    }

    public void setProcedureService(Service procedureService) {
        this.procedureService = (ProcedureService) procedureService;
    }

    public void setUserService(Service userService) {
        this.userService = (UserService) userService;
    }
}
