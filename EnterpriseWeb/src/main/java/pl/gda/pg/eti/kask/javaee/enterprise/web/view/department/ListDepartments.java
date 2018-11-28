package pl.gda.pg.eti.kask.javaee.enterprise.web.view.department;

import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.users.PermissionService;
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

    @EJB
    private CourierService courierService;

    @EJB
    private PermissionService permissionService;

    private Collection<Department> departments;

    public Collection<Department> getDepartments() {
        return departments != null ? departments : (departments = courierService.findAllDepartments());
    }

    public String removeDepartments(Department department) {
        courierService.removeDepartment(department);
        return "list_departments?faces-redirect=true";
    }

    public boolean canSave(Department department){
        return permissionService.canSaveDepartment(department);
    }

    public boolean canRemove(Department department){
        return permissionService.canRemoveDepartment(department);
    }

    public boolean canSelect(Department department){
        return permissionService.canFindDepartment(department);
    }
}