package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpSlotForm extends ActionForm {

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
        return new ActionErrors();
    }


}
