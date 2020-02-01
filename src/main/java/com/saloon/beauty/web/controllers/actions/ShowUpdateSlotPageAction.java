package com.saloon.beauty.web.controllers.actions;

import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.Service;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.ServletResources;
import com.saloon.beauty.web.controllers.forms.ActionForm;
import com.saloon.beauty.web.controllers.forms.SlotIdForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Action for showing the slot updating page
 */
public class ShowUpdateSlotPageAction extends Action {

    private ProcedureService procedureService;
    private UserService userService;
    private SlotService slotService;

    /**
     * Attaches to the session attributes with masters
     * and procedures lists and shows the updating page
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

        Optional<SlotDto> slotOptional = getSlotInformationById(form);

        if (slotOptional.isPresent()){
            SlotDto slot = slotOptional.get();
            request.setAttribute("slot", slot);
        }else if(slotOptional.isEmpty()){
            request.setAttribute("actionResult", "slot.error.notExist");
            return resources.getForward("ShowSlotSearchAdminPage");
        }

        return resources.getForward("ShowSlotUpdatePage");
    }

    Optional<SlotDto> getSlotInformationById(ActionForm form) {

        long id = ((SlotIdForm)form).getSlotId();

        return slotService.getSlotDtoById(id);
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

    public void setSlotService(Service slotService) {
        this.slotService = (SlotService) slotService;
    }

    public void setUserService(Service userService) {
        this.userService = (UserService) userService;
    }
}
