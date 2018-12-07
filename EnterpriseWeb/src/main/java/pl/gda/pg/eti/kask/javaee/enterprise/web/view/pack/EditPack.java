package pl.gda.pg.eti.kask.javaee.enterprise.web.view.pack;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Pack;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.TypeSize;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.users.PermissionService;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Named
@ViewScoped
public class EditPack implements Serializable {

    @Inject
    private CourierService courierService;

    @Inject
    AuthContext authContext;

    @Getter
    @Setter
    private Pack pack = new Pack();

    private Collection<SelectItem> typeSizeAsSelectItems;


    public Collection<SelectItem> getTypeSizeAsSelectItems() {
        if (typeSizeAsSelectItems == null) {
            typeSizeAsSelectItems = new ArrayList<>();
            for (TypeSize typeSize : TypeSize.values()) {
                typeSizeAsSelectItems.add(new SelectItem(typeSize, typeSize.toString()));
            }
        }
        return typeSizeAsSelectItems;
    }


    public String savePack() {
        pack.setOwner(authContext.getCurrentUser());
        courierService.savePack(pack);
        return "list_packs?faces-redirect=true";
    }

    public boolean canSave(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER)||
                authContext.isUserInRole(User.Roles.WORKER);
    }
}
