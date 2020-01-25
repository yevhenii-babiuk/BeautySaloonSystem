package com.saloon.beauty.web.controllers.forms;


import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains html-form data with procedure ID
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcedureIdForm extends ActionForm {

    /**
     * ID of procedure
     */
    private long procedureId;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(HttpServletRequest request) {
        procedureId = getLongPropertyFromRequest(request, "procedureId");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate() {
        ActionErrors errors = new ActionErrors();
        if (procedureId == 0) {
            errors.addError("procedureId", "no procedure id");
        }
        return errors;
    }
}
