package pl.gda.pg.eti.kask.javaee.enterprise.books;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Pack;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collection;


@ApplicationScoped
public class CourierService implements Serializable {

    @PersistenceContext
    EntityManager em;

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
    public Collection<Courier> findAvailableCouriers() {
        TypedQuery<Courier> query = em.createNamedQuery(Courier.Queries.FIND_ALL, Courier.class);
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

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER})
    @Transactional
    public void removePack(Pack pack) {
        pack = em.merge(pack);
        for(Courier courier:pack.getCouriers()){
            courier.getPacks().remove(pack);
        }
        em.remove(pack);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER})
    @Transactional
    public void removeCourier(Courier courier) {
        courier = em.merge(courier);
        em.remove(courier);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER})
    @Transactional
    public void removeDepartment(Department department) {
        department = em.merge(department);
        em.remove(department);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    @Transactional
    public Pack savePack(Pack pack) {
        if (pack.getId() == null) {
            em.persist(pack);
        } else {
            pack = em.merge(pack);
        }

        return pack;
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER})
    @Transactional
    public Courier saveCourier(Courier courier) {
        if (courier.getId() == null) {
            em.persist(courier);
        } else {
            courier = em.merge(courier);
        }

        return courier;
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER})
    @Transactional
    public Department saveDepartment(Department department){
        if (department.getId() == null) {
            em.persist(department);
        } else {
            department = em.merge(department);
        }
        return department;
    }
}