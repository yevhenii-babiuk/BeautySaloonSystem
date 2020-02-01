package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.repository.entity.Role;
import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Class represents adding by administrator user html-form
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserForm extends ActionForm {

    //Form fields
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private Role role;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(HttpServletRequest request) {

        role = getPropertyFromRequest(request,"role").equals("") ? null : Role.valueOf(getPropertyFromRequest(request,"role"));
        firstName = getPropertyFromRequest(request, "firstName");
        lastName = getPropertyFromRequest(request, "lastName");
        email = getPropertyFromRequest(request, "email");
        phone = getPropertyFromRequest(request, "phone");
        password = getPropertyFromRequest(request, "password");
        confirmPassword = getPropertyFromRequest(request, "confirmPassword");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate() {

        ActionErrors errors = new ActionErrors();

        String namePattern = "^[\\p{Alpha}]+$";
        Pattern pattern = Pattern.compile(namePattern, Pattern.UNICODE_CHARACTER_CLASS);

        Matcher matcher = pattern.matcher(firstName);
        if (!matcher.matches()) {
            errors.addError("firstName", "registration.error.firstName");
        }

        matcher = pattern.matcher(lastName);
        if (!matcher.matches()) {
            errors.addError("lastName", "registration.error.lastName");
        }

        if (!email.matches("^[A-z\\d._]+@[A-z]+\\.[A-z]+$")) {
            errors.addError("email", "registration.error.email");
        }

        if (!phone.matches("^[-+\\d]{10,15}$")) {
            errors.addError("phone", "registration.error.phone");
        }

        if (password.length() < 5) {
            errors.addError("password", "registration.error.password");
            eraseWrongPasswords();
        }

        if (!password.equals(confirmPassword)) {
            errors.addError("confirmPassword", "registration.error.confirmPassword");
            eraseWrongPasswords();
        }

        if(role == null || Stream.of("ADMINISTRATOR", "MASTER", "USER").
                noneMatch(r->r.equals(this.role.name()))){
            errors.addError("role", "registration.error.role");
        }

        return errors;
    }

    /**
     * Erases passwords data from form if some
     * errors with passwords occurred
     */
    private void eraseWrongPasswords() {
        password = "";
        confirmPassword = "";
    }
}
