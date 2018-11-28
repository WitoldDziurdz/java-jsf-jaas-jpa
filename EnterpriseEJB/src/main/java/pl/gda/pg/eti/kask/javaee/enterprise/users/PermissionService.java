package pl.gda.pg.eti.kask.javaee.enterprise.users;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.*;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PermissionService {


    @Resource
    SessionContext sessionCtx;

    @PersistenceContext
    EntityManager em;

    @Inject
    UserService userService;

    public boolean canFindPack(Pack pack) {
        return isOperation(Role.Operations.FIND_PACK, pack);
    }

    public boolean canFindCourier(Courier courier) {
        return isOperation(Role.Operations.FIND_COURIER, courier);
    }

    public boolean canFindDepartment(Department department) {
        return isOperation(Role.Operations.FIND_DEPARTMENT, department);
    }

    public boolean canRemovePack(Pack pack) {
        return isOperation(Role.Operations.REMOVE_PACK, pack);
    }

    public boolean canRemoveCourier(Courier courier) {
        return isOperation(Role.Operations.REMOVE_COURIER, courier);
    }

    public boolean canRemoveDepartment(Department department) {
        return isOperation(Role.Operations.REMOVE_DEPARTMENT, department);
    }

    public boolean canSavePack(Pack pack) {
        return isOperation(Role.Operations.SAVE_PACK, pack);
    }

    public boolean canSaveCourier(Courier courier) {
        return isOperation(Role.Operations.SAVE_COURIER, courier);
    }

    public boolean canSaveDepartment(Department department) {
        return isOperation(Role.Operations.SAVE_DEPARTMENT, department);
    }

    public boolean isOperation(String operation, Element element) {
        TypedQuery<Role> query = em.createNamedQuery(Role.Queries.FIND_ROLE, Role.class);
        query.setParameter("operation", operation);
        List<Role> roles = query.getResultList();
        List<String> userRoles = userService.findCurrentUser().getRoles();
        for (Role role : roles) {
            if (userRoles.contains(role.getRole())) {
                if (isGranted(role.getPermission())) {
                    return true;
                } else if (isOwner(role.getPermission())) {
                    return isNewOrOwnerElement(element);
                }
            }
        }
        return false;
    }

    private boolean isNewOrOwnerElement(Element element) {
        if (isNewElement(element)) {
            return true;
        }
        if (element instanceof Pack) {
            Pack pack = (Pack) element;
            return isPackOwner(pack);
        } else if (element instanceof Courier) {
            Courier courier = (Courier) element;
            return isCourierOwner(courier);
        } else if (element instanceof Department) {
            Department department = (Department) element;
            return isDepartmentOwner(department);
        }
        return false;
    }


    private boolean isGranted(String permission) {
        return permission.equals(Role.Permission.GRANTED);
    }

    private boolean isOwner(String permission) {
        return permission.equals(Role.Permission.IF_OWNER);
    }

    private boolean isNewElement(Element element) {
        if (element != null) {
            return element.getId() == null;
        }
        return false;
    }


    private boolean isPackOwner(Pack pack) {
        if (pack != null) {
            String login = sessionCtx.getCallerPrincipal().getName();
            Pack originalPack = em.find(Pack.class, pack.getId());
            em.detach(originalPack);
            return originalPack.getOwner().getLogin().equals(login);
        }
        return false;
    }


    private boolean isDepartmentOwner(Department department) {
        if (department != null) {
            String login = sessionCtx.getCallerPrincipal().getName();
            Department origin = em.find(Department.class, department.getId());
            em.detach(origin);
            return origin.getOwner().getLogin().equals(login);
        }
        return false;
    }

    private boolean isCourierOwner(Courier courier) {
        if (courier != null) {
            String login = sessionCtx.getCallerPrincipal().getName();
            Courier origin = em.find(Courier.class, courier.getId());
            em.detach(origin);
            return origin.getOwner().getLogin().equals(login);
        }
        return false;
    }
}
