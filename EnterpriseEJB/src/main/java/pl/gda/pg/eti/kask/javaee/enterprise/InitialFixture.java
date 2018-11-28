package pl.gda.pg.eti.kask.javaee.enterprise;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.*;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Role.Operations;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Role.Permission;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User.Roles;
import pl.gda.pg.eti.kask.javaee.enterprise.users.CryptUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Arrays.asList;

@Singleton
@Startup
@Log
public class InitialFixture {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {

            initPerm();
            User admin = new User("admin", CryptUtils.sha256("admin"), asList(Roles.ADMIN, Roles.MANAGER, Roles.WORKER));
            User manager1 = new User("manager1", CryptUtils.sha256("manager1"), asList(Roles.MANAGER));
            User manager2 = new User("manager2", CryptUtils.sha256("manager2"), asList(Roles.MANAGER));
            User worker1 = new User("worker1", CryptUtils.sha256("worker1"), asList(Roles.WORKER));
            User worker2 = new User("worker2", CryptUtils.sha256("worker2"), asList(Roles.WORKER));
            List<User> users = asList(admin, manager1, manager2, worker1, worker2);

            users.forEach(user -> em.persist(user));
            em.flush();

            Pack p1 = new Pack("Gdansk wyspianskiego 9", TypeSize.LARGE, 10.22, false,worker1);
            Pack p2 = new Pack("Gdansk wyspianskiego 12", TypeSize.SMALL, 5, true, worker1);
            Pack p3 = new Pack("Gdansk wyspianskiego 22", TypeSize.MEDIUM, 5, true, worker2);

            Department d1 = new Department(12, "Warszawa", false);
            Department d2 = new Department(22, "Gdansk", true);

            Courier c1 = new Courier("Hubert", "Polak", "570434267", 22, asList(p1, p2), d1);
            Courier c2 = new Courier("Piotr", "Majewski", "570434211", 44, asList(p3), d2);
            Courier c3 = new Courier("Krystian", "Olejnik", "570431267", 15, asList(p1, p2), d1);
            Courier c4 = new Courier("Oskar", "Nawrocki", "570434331", 19, asList(p3), d2);

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(d1);
            em.persist(d2);

    }

    private void initPerm() {
        addManagerPerm();
        addWorkerPerm();
    }

    private void addManagerPerm() {
        em.persist(new Role(Roles.MANAGER, Operations.FIND_PACK, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.FIND_COURIER, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.FIND_DEPARTMENT, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.REMOVE_PACK, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.REMOVE_COURIER, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.REMOVE_DEPARTMENT, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.SAVE_PACK, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.SAVE_COURIER, Permission.GRANTED));
        em.persist(new Role(Roles.MANAGER, Operations.SAVE_DEPARTMENT, Permission.GRANTED));
    }

    private void addWorkerPerm() {
        em.persist(new Role(Roles.WORKER, Operations.FIND_PACK, Permission.GRANTED));
        em.persist(new Role(Roles.WORKER, Operations.FIND_COURIER, Permission.GRANTED));
        em.persist(new Role(Roles.WORKER, Operations.FIND_DEPARTMENT, Permission.GRANTED));
        em.persist(new Role(Roles.WORKER, Operations.REMOVE_PACK, Permission.IF_OWNER));
        em.persist(new Role(Roles.WORKER, Operations.REMOVE_COURIER, Permission.DENIED));
        em.persist(new Role(Roles.WORKER, Operations.REMOVE_DEPARTMENT, Permission.DENIED));
        em.persist(new Role(Roles.WORKER, Operations.SAVE_PACK, Permission.IF_OWNER));
        em.persist(new Role(Roles.WORKER, Operations.SAVE_COURIER, Permission.DENIED));
        em.persist(new Role(Roles.WORKER, Operations.SAVE_DEPARTMENT, Permission.DENIED));
    }
}
