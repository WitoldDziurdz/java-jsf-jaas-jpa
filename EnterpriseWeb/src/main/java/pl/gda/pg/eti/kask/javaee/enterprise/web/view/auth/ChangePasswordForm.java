package pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.omnifaces.util.Utils;
import pl.gda.pg.eti.kask.javaee.enterprise.users.CryptUtils;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;
import pl.gda.pg.eti.kask.javaee.enterprise.web.JSFUtils;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class ChangePasswordForm implements Serializable {

    @EJB
    UserService userService;

    @Getter
    @Setter
    String oldPassword;

    @Getter
    @Setter
    String newPassword1;

    @Getter
    @Setter
    String newPassword2;

    public String changePassword() throws IOException {
        try {
            if(newPassword1.equals(newPassword2)){
                userService.changePassword(CryptUtils.sha256(newPassword1),CryptUtils.sha256(oldPassword));
            }
            else {
                Messages.addError("password-form", "Błąd,nowe hasła różnią się.");
                return null;
            }
            return "/couriers/list_couriers.xhtml?faces-redirect=true";

        } catch (ServletException ex) {
            Messages.addError("password-form", "Błąd, stare hasło jest niepoprawne");
            return null;
        }
    }
}
