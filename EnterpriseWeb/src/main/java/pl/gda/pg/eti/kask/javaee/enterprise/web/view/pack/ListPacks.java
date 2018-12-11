package pl.gda.pg.eti.kask.javaee.enterprise.web.view.pack;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.*;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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
    private String address;

    @Getter
    @Setter
    private TypeSize typeSize;

    @Getter
    @Setter
    private Double price;

    @Getter
    @Setter
    private Boolean express;

    private Collection<SelectItem> typeSizeAsSelectItems;

    @Getter
    @Setter
    private Collection<Pack> packs = null;

    public Collection<Pack> findPacks() {
        packs = courierService.findAllPacksByParameters(address,typeSize,price,express);
        return packs;
    }

    public Collection<Pack> getPacks() {
        if (packs == null) {
            if (courier == null) {
                packs = courierService.findAllPacks();
            } else {
                packs = courierService.findPacksOfCourier(courier);
            }
        }
        return packs;
    }

    public Collection<SelectItem> getTypeSizeAsSelectItems() {
        if (typeSizeAsSelectItems == null) {
            typeSizeAsSelectItems = new ArrayList<>();
            for (TypeSize typeSize : TypeSize.values()) {
                typeSizeAsSelectItems.add(new SelectItem(typeSize, typeSize.toString()));
            }
        }
        return typeSizeAsSelectItems;
    }

    public void removePack(Pack pack) {
        packs.remove(pack);
        courierService.removePack(pack);
    }

    public void sortById(){
        packs = courierService.findAllPacksBySort(TypeSort.ID);
        System.out.println("start sort id");
    }

    public void sortByAddress(){
        packs = courierService.findAllPacksBySort(TypeSort.ADDRESS);
    }

    public void sortByPrice(){

        packs = courierService.findAllPacksBySort(TypeSort.PRICE);
    }

    public void sortByTypeSize(){

        packs = courierService.findAllPacksBySort(TypeSort.TYPE_SIZE);
    }

    public void sortByExpress(){

        packs = courierService.findAllPacksBySort(TypeSort.EXPRESS);
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
