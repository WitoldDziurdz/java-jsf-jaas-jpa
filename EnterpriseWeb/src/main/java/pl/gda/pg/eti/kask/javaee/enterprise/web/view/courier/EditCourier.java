package pl.gda.pg.eti.kask.javaee.enterprise.web.view.courier;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Pack;
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
public class EditCourier implements Serializable {

    @Inject
    AuthContext authContext;

    @EJB
    private CourierService courierService;

    @Getter
    @Setter
    private Courier courier = new Courier();

    public Collection<Pack> getAvailablePacks() {
        return courierService.findAllPacks();
    }

    public Collection<Department> getAvailableDepartments() {
        return courierService.findAllDepartments();
    }

    public String saveCourier() {
        courierService.saveCourier(courier);
        return "list_couriers?faces-redirect=true";
    }

    public boolean canSave(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER)||
                authContext.isUserInRole(User.Roles.WORKER);
    }
}
