package pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth;


import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.users.CryptUtils;
import pl.gda.pg.eti.kask.javaee.enterprise.users.RegistrationService;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

@Named
@ViewScoped
public class RegistrationForm implements Serializable {

    @EJB
    RegistrationService registrationService;

    @Getter
    @Setter
    String login;

    @Getter
    @Setter
    String password1;

    @Getter
    @Setter
    String password2;

    @Getter
    @Setter
    private String role;


    private Collection<SelectItem> rolesAsSelectItems;


    public Collection<SelectItem> getRolesSizeAsSelectItems() {
        List<String> roles = new ArrayList<>();
        roles.add(User.Roles.MANAGER);
        roles.add(User.Roles.WORKER);
        if (rolesAsSelectItems == null) {
            rolesAsSelectItems = new ArrayList<>();
            for (String role : roles) {
                rolesAsSelectItems.add(new SelectItem(role, role));
            }
        }
        return rolesAsSelectItems;
    }

    public String registration() {
        try {
            if(password1.equals(password2)){
                User user = new User(login, CryptUtils.sha256(password1), asList(role));
                registrationService.addUser(user);
                return "/auth/custom/login_form.xhtml?faces-redirect=true";
            }
            else {
                Messages.addError("login-form", "Hasła są różne");
                return null;
            }

        } catch (ServletException ex) {
            Messages.addError("login-form", "Taki uzytkownik juz istnieje");
            return null;
        }
    }

}
