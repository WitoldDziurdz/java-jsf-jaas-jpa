package pl.gda.pg.eti.kask.javaee.enterprise.couriers;

import pl.gda.pg.eti.kask.javaee.enterprise.couriers.auth.RoleAnnotation;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.*;
import pl.gda.pg.eti.kask.javaee.enterprise.events.CourierEvent;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.CourierCreation;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.CourierDeletion;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.CourierModification;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collection;


@Stateless
public class CourierService implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Inject
    Event<CourierEvent> courierEvent;

    @Inject
    UserService userService;

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Collection<Pack> findAllPacks() {
        TypedQuery<Pack> query = em.createNamedQuery(Pack.Queries.FIND_ALL, Pack.class);
        return query.getResultList();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Collection<Pack> findPacksByPrice(double price) {
        TypedQuery<Pack> query = em.createNamedQuery(Pack.Queries.FIND_PACKS, Pack.class)
                .setParameter("price", price);
        return query.getResultList();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Collection<Pack> findPacksOfCourier(Courier courier) {
        return courier.getPacks();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Collection<Courier> findAllCouriers() {
        TypedQuery<Courier> query = em.createNamedQuery(Courier.Queries.FIND_ALL, Courier.class);
        return query.getResultList();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Collection<Courier> findAllSortCouriers() {
        TypedQuery<Courier> query = em.createNamedQuery(Courier.Queries.FIND_ALL_SORT, Courier.class);
        return query.getResultList();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Collection<Department> findAllDepartments() {
        TypedQuery<Department> query = em.createNamedQuery(Department.Queries.FIND_ALL, Department.class);
        return query.getResultList();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Collection<Courier> findCouriersOfDepartment(Department department) {
        return department.getCouriers();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Pack findPack(int id) {
        return em.find(Pack.class, id);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Courier findCourier(int id) {
        return em.find(Courier.class, id);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public Department findDepartment(int id) {
        return em.find(Department.class, id);
    }

    @RoleAnnotation
    @Transactional
    public void removePack(Pack pack) {
        pack = em.merge(pack);
        for(Courier courier:pack.getCouriers()){
            courier.getPacks().remove(pack);
        }
        updateCouriers(pack.getCouriers());
        em.remove(pack);
    }

    @RoleAnnotation
    @Transactional
    public void removeCourier(Courier courier) {
        courier = em.merge(courier);
        em.remove(courier);
        courierEvent.select(CourierDeletion.Literal).fire(CourierEvent.of(courier));
    }

    @RoleAnnotation
    @Transactional
    public void removeDepartment(Department department) {
        department = em.merge(department);
        Collection<Courier> couriers = department.getCouriers();
        em.remove(department);
        deleteCouriers(couriers);
    }

    @RoleAnnotation
    @Transactional
    public Pack savePack(Pack pack) {
        if (pack.getId() == null) {
            setOwner(pack);
            em.persist(pack);
        } else {
            pack = em.merge(pack);
            updateCouriers(pack.getCouriers());
        }

        return pack;
    }

    @RoleAnnotation
    @Transactional
    public Courier saveCourier(Courier courier) {
        if (courier.getId() == null) {
            setOwner(courier);
            em.persist(courier);
            courierEvent.select(CourierCreation.Literal).fire(CourierEvent.of(courier));
        } else {
            courier = em.merge(courier);
            courierEvent.select(CourierModification.Literal).fire(CourierEvent.of(courier));
        }

        return courier;
    }

    @RoleAnnotation
    @Transactional
    public Department saveDepartment(Department department){
        if (department.getId() == null) {
            setOwner(department);
            em.persist(department);
        } else {
            department = em.merge(department);
            updateCouriers(department.getCouriers());
        }
        return department;
    }

    private void updateCouriers(Collection<Courier> couriers){
        for(Courier courier: couriers){
            courierEvent.select(CourierModification.Literal).fire(CourierEvent.of(courier));
        }
    }

    private void deleteCouriers(Collection<Courier> couriers){
        for(Courier courier: couriers){
            courierEvent.select(CourierDeletion.Literal).fire(CourierEvent.of(courier));
        }
    }

    private void setOwner(Element element){
        element.setOwner(userService.findCurrentUser());
    }
}