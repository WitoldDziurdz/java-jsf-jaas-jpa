package pl.gda.pg.eti.kask.javaee.enterprise.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class JSFUtils {

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request;
    }

    public static void addMessage(String clientId, String msg) {
        FacesMessage errorMsg = new FacesMessage(msg);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(clientId, errorMsg);
    }

    public static void addError(String clientId, String msg) {
        FacesMessage errorMsg = new FacesMessage(msg);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(clientId, errorMsg);
    }
}
