package pl.gda.pg.eti.kask.javaee.enterprise.web.view.department;

import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@RequestScoped
public class ListDepartments implements Serializable {

    @Inject
    AuthContext authContext;

    @EJB
    private CourierService courierService;

    private Collection<Department> departments;

    public Collection<Department> getDepartments() {
        return departments != null ? departments : (departments = courierService.findAllDepartments());
    }

    public String removeDepartments(Department department) {
        courierService.removeDepartment(department);
        return "list_departments?faces-redirect=true";
    }

    public boolean canSave(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER)||
                authContext.isUserInRole(User.Roles.WORKER);
    }


    public boolean canRemove(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER)||
                authContext.isUserInRole(User.Roles.WORKER);
    }

    public boolean canSelect(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER)||
                authContext.isUserInRole(User.Roles.WORKER);
    }
}