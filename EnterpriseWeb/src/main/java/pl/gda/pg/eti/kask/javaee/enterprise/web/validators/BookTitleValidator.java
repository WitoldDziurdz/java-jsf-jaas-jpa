package pl.gda.pg.eti.kask.javaee.enterprise.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

/**
 * @author psysiu
 */
@ManagedBean
@RequestScoped
public class BookTitleValidator implements Validator {

    private static final String PATTERN = "^\\p{Lu}(\\p{L}|\\p{N})*([ -](\\p{L}|\\p{N})*)*$";

    private static final String WRONG_TITLE = "pl.gda.pg.eti.kask.javaee.enterprise.validators.titleValidator.WRONG_TITLE";

    private static final String WRONG_VALUE = "pl.gda.pg.eti.kask.javaee.enterprise.validators.titleValidator.WRONG_VALUE";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value instanceof String) {
            String title = (String) value;
            if (!Pattern.matches(PATTERN, title)) {
                throw new ValidatorException(new FacesMessage(ValidatorUtils.getMessageBundle().getString(WRONG_TITLE)));
            }
        } else {
            throw new ValidatorException(new FacesMessage(ValidatorUtils.getMessageBundle().getString(WRONG_VALUE)));
        }
    }
}
