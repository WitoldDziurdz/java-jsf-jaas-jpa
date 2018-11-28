package pl.gda.pg.eti.kask.javaee.enterprise.web.view.courier;

import lombok.Getter;
import lombok.Setter;
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
public class ListCouriers implements Serializable {
    @EJB
    private CourierService courierService;

    @EJB
    private PermissionService permissionService;

    @Getter
    @Setter
    private Department department = null;

    private Collection<Courier> couriers;

    public Collection<Courier> getCouriers() {
        if (couriers == null) {
            if(department == null) {
                couriers = courierService.findAllCouriers();
            }else {
                couriers = courierService.findCouriersOfDepartment(department);
            }
        }
        return couriers;
    }

    public String removeCourier(Courier courier) {
        courierService.removeCourier(courier);
        return "list_couriers?faces-redirect=true";
    }


    public boolean canSave(Courier courier){
        return permissionService.canSaveCourier(courier);
    }

    public boolean canRemove(Courier courier){
        return permissionService.canRemoveCourier(courier);
    }

    public boolean canSelect(Courier courier){
        return permissionService.canFindCourier(courier);
    }

}