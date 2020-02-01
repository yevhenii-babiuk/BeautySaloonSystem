package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class represents feedback searching html-form
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackSearchForm extends ActionForm {

    private long masterId;
    private LocalDate minDate;
    private LocalDate maxDate;
    private LocalTime minStartTime;
    private LocalTime maxStartTime;
    private long procedure;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(HttpServletRequest request) {
        masterId = getLongPropertyFromRequest(request, "masterId");
        minDate = getDatePropertyFromRequest(request, "minDate");
        maxDate = getDatePropertyFromRequest(request, "maxDate");
        minStartTime = getTimePropertyFromRequest(request, "minTime");
        maxStartTime = getTimePropertyFromRequest(request, "maxTime");
        procedure = getLongPropertyFromRequest(request, "procedureId");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate() {
        return new ActionErrors();
    }


}
