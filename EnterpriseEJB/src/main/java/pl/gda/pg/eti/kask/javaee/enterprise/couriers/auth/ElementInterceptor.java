package pl.gda.pg.eti.kask.javaee.enterprise.books.auth;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Role;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.Method;
import java.util.List;

public abstract class ElementInterceptor {

    @Resource
    SessionContext sessionCtx;

    @PersistenceContext
    EntityManager em;

    @Inject
    UserService userService;

    protected boolean isOperation(Method method) {
        TypedQuery<Role> query = em.createNamedQuery(Role.Queries.FIND_ROLE, Role.class);
        query.setParameter( "operation", method.getName());
        List<Role> roles = query.getResultList();
        List<String> userRoles = userService.findCurrentUser().getRoles();
        for(Role role: roles){
            if(userRoles.contains(role.getRole())){
                if(isGranted(role.getPermission())){
                    return true;
                }
                else if(isOwner(role.getPermission())){
                    return isNewOrOwnerElement();
                }
            }
        }
        return false;
    }

    protected boolean isGranted(String permission){
        return permission.equals(Role.Permission.GRANTED);
    }

    protected boolean isOwner(String permission){
        return permission.equals(Role.Permission.IF_OWNER);
    }

    protected abstract boolean isNewOrOwnerElement();
}
