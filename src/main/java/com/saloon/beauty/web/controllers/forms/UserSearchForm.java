package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.repository.entity.Status;
import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class represents user searching html-form
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchForm extends ActionForm {

    private Role role;
    private String searchString;
    private String email;
    private String phone;

    @Override
    public void fill(HttpServletRequest request) {
        role = getPropertyFromRequest(request,"role") == "" ? null : Role.valueOf(getPropertyFromRequest(request,"role"));
        searchString = getPropertyFromRequest(request, "searchString");
        email = getPropertyFromRequest(request, "email");
        phone = getPropertyFromRequest(request, "phone");
    }

    @Override
    public ActionErrors validate() {
        return new ActionErrors();
    }


}
