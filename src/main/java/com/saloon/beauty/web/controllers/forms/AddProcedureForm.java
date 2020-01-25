package com.saloon.beauty.web.controllers.forms;

import com.saloon.beauty.web.controllers.ActionErrors;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains html-form data for adding procedure
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProcedureForm extends ActionForm{

    private String nameEnglish;
    private String nameUkrainian;
    private String nameRussian;
    private String descriptionEnglish;
    private String descriptionUkrainian;
    private String descriptionRussian;
    private int price;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill(HttpServletRequest request) {

        nameEnglish = getPropertyFromRequest(request, "nameEn");
        nameUkrainian = getPropertyFromRequest(request, "nameUa");
        nameRussian = getPropertyFromRequest(request, "nameRu");
        descriptionRussian = getPropertyFromRequest(request, "descriptionRu");
        descriptionUkrainian = getPropertyFromRequest(request, "descriptionUa");
        descriptionEnglish = getPropertyFromRequest(request, "descriptionEn");
        price = (int)getLongPropertyFromRequest(request, "procedurePrice");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate() {
        ActionErrors errors = new ActionErrors();

        if (descriptionEnglish.isEmpty()) {
            errors.addError("englishDescription", "procedureManagement.addProcedure.errors.englishDescription");
        }

        if (descriptionUkrainian.isEmpty()) {
            errors.addError("ukrainianDescription", "procedureManagement.addProcedure.errors.ukrainianDescription");
        }

        if (descriptionRussian.isEmpty()) {
            errors.addError("russianDescription", "procedureManagement.addProcedure.errors.russianDescription");
        }

        if (nameEnglish.isEmpty()) {
            errors.addError("englishName", "procedureManagement.addProcedure.errors.englishName");
        }

        if (nameUkrainian.isEmpty()) {
            errors.addError("ukrainianName", "procedureManagement.addProcedure.errors.ukrainianName");
        }

        if (nameRussian.isEmpty()) {
            errors.addError("russianName", "procedureManagement.addProcedure.errors.russianName");
        }

        if (price==0) {
            errors.addError("price", "procedureManagement.addProcedure.errors.inValidPrice");
        }

        return errors;
    }
}
