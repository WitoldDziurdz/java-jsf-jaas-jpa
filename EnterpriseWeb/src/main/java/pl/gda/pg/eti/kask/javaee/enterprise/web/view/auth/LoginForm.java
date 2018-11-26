package pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth;


import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import pl.gda.pg.eti.kask.javaee.enterprise.web.JSFUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginForm implements Serializable {
    @Getter
    @Setter
    String login;

    @Getter
    @Setter
    String password;

    public String login() throws IOException {
        try {
            HttpServletRequest request = JSFUtils.getRequest();
            request.login(login, password);
            return "/couriers/list_couriers.xhtml?faces-redirect=true";

        } catch (ServletException ex) {
            Messages.addError("login-form", "Login failed");
            return null;
        }
    }

}
