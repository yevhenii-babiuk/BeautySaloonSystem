package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.repository.entity.Procedure;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.repository.entity.User;
import com.saloon.beauty.services.ProcedureService;
import com.saloon.beauty.services.ServiceFactory;
import com.saloon.beauty.services.UserService;
import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class represents slot adding html-form
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotForm extends ActionForm {

    private ProcedureService procedureService;
    private UserService userService;

    private long masterId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private long procedure;

    @Override
    public void fill(HttpServletRequest request) {
        masterId = getLongPropertyFromRequest(request, "masterId");
        date = getDatePropertyFromRequest(request, "date");
        startTime = getTimePropertyFromRequest(request, "startTime");
        endTime = getTimePropertyFromRequest(request, "endTime");
        procedure = getLongPropertyFromRequest(request, "procedureId");
    }

    @Override
    public ActionErrors validate() {

        setServices();

        ActionErrors errors = new ActionErrors();

        if (masterId == 0 ||
                userService.getAllMasters()
                        .stream()
                        .map(User::getId)
                        .noneMatch(id -> id.equals(masterId))) {
            errors.addError("master", "slot.error.master");
        }

        if (procedure == 0 ||
                procedureService.getAllProcedure()
                .stream()
                .map(Procedure::getId)
                .noneMatch(id -> id.equals(procedure))) {
            errors.addError("procedure", "slot.error.procedure");
        }

        if (date == null || date.isBefore(LocalDate.now())) {
            errors.addError("date", "slot.error.date");
        }

        if (startTime == null || startTime.isBefore(LocalTime.now())){
            errors.addError("startTime", "slot.error.startTime");
        }

        if (endTime == null || endTime.isBefore(startTime)){
            errors.addError("endTime", "slot.error.endTime");
        }

        return errors;
    }

    private void setServices() {
        procedureService = (ProcedureService) new ServiceFactory().
                getService("com.saloon.beauty.services.ProcedureService");;
        userService = (UserService) new ServiceFactory().
                getService("com.saloon.beauty.services.UserService");
    }
}


