package pl.gda.pg.eti.kask.javaee.enterprise.web.view;

import org.omnifaces.util.Faces;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.web.JSFUtils;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@Named
@RequestScoped
public class Main implements Serializable {

    @Inject
    AuthContext authContext;

    public void logout() throws ServletException, IOException {
        HttpServletRequest request = JSFUtils.getRequest();
        request.logout();
        request.getSession().invalidate();
        Faces.redirect("/");
    }

    public boolean isAdmin(){
        return authContext.isUserInRole(User.Roles.ADMIN);
    }

}
