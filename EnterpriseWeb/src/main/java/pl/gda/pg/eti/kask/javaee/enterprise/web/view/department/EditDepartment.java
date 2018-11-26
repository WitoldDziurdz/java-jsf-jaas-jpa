package pl.gda.pg.eti.kask.javaee.enterprise.web.view.department;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
public class EditDepartment implements Serializable {

    @Inject
    AuthContext authContext;

    @EJB
    private CourierService courierService;

    @Getter
    @Setter
    private Department department = new Department();

    public Collection<Courier> getAvailableCouriers() {
        return courierService.findAvailableCouriers();
    }
    public String saveDepartment() {
        courierService.saveDepartment(department);
        return "list_departments?faces-redirect=true";
    }

    public boolean canSave(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER);
    }
}
