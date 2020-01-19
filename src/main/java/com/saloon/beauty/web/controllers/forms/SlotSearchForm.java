package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class represents slot searching html-form
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotSearchForm extends ActionForm {

    private long masterId;
    private Status status;
    private LocalDate minDate;
    private LocalDate maxDate;
    private LocalTime minStartTime;
    private LocalTime maxStartTime;
    private long procedure;

    @Override
    public void fill(HttpServletRequest request) {
        masterId = getLongPropertyFromRequest(request, "masterId");
        status = getPropertyFromRequest(request,"status") == "" ? null : Status.valueOf(getPropertyFromRequest(request,"status"));
        minDate = getDatePropertyFromRequest(request, "minDate");
        maxDate = getDatePropertyFromRequest(request, "maxDate");
        minStartTime = getTimePropertyFromRequest(request, "minTime");
        maxStartTime = getTimePropertyFromRequest(request, "maxTime");
        procedure = getLongPropertyFromRequest(request, "procedureId");
    }

    @Override
    public ActionErrors validate() {
        return new ActionErrors();
    }


}
