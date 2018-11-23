package pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth;

import lombok.Getter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListUsers implements Serializable {

    @Inject
    AuthContext authContext;

    @EJB
    UserService userService;

    @Getter
    List<User> users;

    @PostConstruct
    public void init() {
        users = userService.findAllUsers();
    }

    public boolean canEdit(User user){
        return authContext.isUserInRole(User.Roles.ADMIN) && (!user.getRoles().contains(User.Roles.ADMIN));
    }
}
