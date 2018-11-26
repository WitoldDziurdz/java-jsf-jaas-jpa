package pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Named
@ViewScoped
public class EditUser implements Serializable {
    @Getter
    @Setter
    private String login;

    @EJB
    UserService userService;

    @Getter
    @Setter
    private User user = null;

    @Getter
    @Setter
    private List<String> roles = new ArrayList();

    @Inject
    AuthContext authContext;

    private Collection<SelectItem> rolesAsSelectItems;


    public Collection<SelectItem> getRolesSizeAsSelectItems() {
        List<String> roles = new ArrayList<>();
        roles.add(User.Roles.ADMIN);
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

    public void preRenderView(){
        user = userService.findUser(login);
    }

    public String save(){
        if(user!=null) {
            user.setRoles(roles);
            userService.saveUser(user);
        }
        return "list_users?faces-redirect=true";
    }


    public boolean canEdit(){
        return authContext.isUserInRole(User.Roles.ADMIN) && (!user.getRoles().contains(User.Roles.ADMIN));
    }
}
