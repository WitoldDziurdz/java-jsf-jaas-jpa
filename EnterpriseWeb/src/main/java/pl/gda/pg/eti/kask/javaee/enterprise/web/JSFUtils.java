package pl.gda.pg.eti.kask.javaee.enterprise.web;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class JSFUtils {

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request;
    }
}
