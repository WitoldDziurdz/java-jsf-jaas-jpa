package pl.gda.pg.eti.kask.javaee.enterprise.web.view;

import org.omnifaces.util.Faces;
import pl.gda.pg.eti.kask.javaee.enterprise.web.JSFUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@Named
@RequestScoped
public class Main implements Serializable {

    public void logout() throws ServletException, IOException {
        HttpServletRequest request = JSFUtils.getRequest();
        request.logout();
        request.getSession().invalidate();
        Faces.redirect("/");
    }
}
