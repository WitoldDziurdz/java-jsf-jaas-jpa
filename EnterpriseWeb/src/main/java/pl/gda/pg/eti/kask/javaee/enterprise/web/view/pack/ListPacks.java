package pl.gda.pg.eti.kask.javaee.enterprise.web.view.pack;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Pack;
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
public class ListPacks implements Serializable {

    @Inject
    AuthContext authContext;

    @EJB
    private CourierService courierService;

    @Getter
    @Setter
    private Courier courier = null;

    @Getter
    @Setter
    private double price;

    private Collection<Pack> packs;

    public Collection<Pack> getPacks() {
        if (packs == null) {
            if (courier == null) {
                if(price > 0.0) {
                    packs = courierService.findAllPacks();
                }else{
                    packs = courierService.findPacksByPrice(price);
                }
                packs = courierService.findPacksByPrice(price);

            } else {
                packs = courierService.findPacksOfCourier(courier);
            }
        }
        return packs;
    }

    public String filtrByPrice() {
        return "list_packs?price="+price+"faces-redirect=true";
    }

    public String removePack(Pack pack) {
        courierService.removePack(pack);
        return "list_packs?faces-redirect=true";
    }

    public boolean canSave(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER);
    }

    public boolean canRemove(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER);
    }

    public boolean canSelect(){
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                authContext.isUserInRole(User.Roles.MANAGER)||
                authContext.isUserInRole(User.Roles.WORKER);
    }
}
