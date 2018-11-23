package pl.gda.pg.eti.kask.javaee.enterprise.web.validators;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

class ValidatorUtils {

    static ResourceBundle getMessageBundle() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String messageBundleName = facesContext.getApplication().getMessageBundle();
        Locale locale = facesContext.getViewRoot().getLocale();
        return ResourceBundle.getBundle(messageBundleName, locale);
    }
}
