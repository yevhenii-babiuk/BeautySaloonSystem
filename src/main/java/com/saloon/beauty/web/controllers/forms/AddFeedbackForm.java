package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains html-form data for adding feedback
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddFeedbackForm extends ActionForm{

    /**
     * ID of slot for adding feedback
     */
    private long slotId;

    private String feedbackText;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(HttpServletRequest request) {

        feedbackText = getPropertyFromRequest(request, "feedbackText");

        slotId = getLongPropertyFromRequest(request, "slotId");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate() {
        ActionErrors errors = new ActionErrors();

        if (feedbackText.isEmpty()) {
            errors.addError("emptyFeedback", "feedbackManagement.addFeedback.errors.text");
        }

        return errors;
    }
}
