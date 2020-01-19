package com.saloon.beauty.web.controllers.forms;


import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains html-form data with slot ID
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotIdForm extends ActionForm {

    /**
     * ID of slot
     */
    private long slotId;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(HttpServletRequest request) {
        slotId = getLongPropertyFromRequest(request, "slotId");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate() {
        ActionErrors errors = new ActionErrors();
        if (slotId == 0) {
            errors.addError("sotId", "no slot id");
        }
        return errors;
    }
}
