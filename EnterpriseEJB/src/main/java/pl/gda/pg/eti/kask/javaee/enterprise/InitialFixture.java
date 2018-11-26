package pl.gda.pg.eti.kask.javaee.enterprise;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.*;
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
        Long authorsCount = em.createNamedQuery(Author.Queries.COUNT_ALL, Long.class).getSingleResult();

        if (authorsCount == 0) {
                List<User> users = asList(
                        new User("admin", CryptUtils.sha256("admin"), asList(Roles.ADMIN, Roles.USER, Roles.MANAGER, Roles.WORKER)),
                        new User("manager1", CryptUtils.sha256("manager1"), asList(Roles.MANAGER)),
                        new User("manager2", CryptUtils.sha256("manager2"), asList(Roles.MANAGER)),
                        new User("worker1", CryptUtils.sha256("worker1"), asList(Roles.WORKER)),
                        new User("worker2", CryptUtils.sha256("worker2"), asList(Roles.WORKER))
                        );

                users.forEach(user -> em.persist(user));
                em.flush();

                Pack p1 = new Pack(
                        "Gdansk wyspianskiego 9",
                        TypeSize.LARGE,
                        10.22,
                        false );
                Pack p2 = new Pack(
                        "Gdansk wyspianskiego 12",
                        TypeSize.SMALL,
                        5,
                        true );
                Pack p3 = new Pack(
                        "Gdansk wyspianskiego 22",
                        TypeSize.MEDIUM,
                        5,
                        true );

                Department d1 = new Department( 12, "Warszawa",false);
                Department d2 = new Department(22, "Gdansk",true);

                Courier c1 = new Courier("Hubert", "Polak","570434267",22,asList(p1,p2), d1);
                Courier c2 = new Courier("Piotr", "Majewski","570434211",44,asList(p3), d2);
                Courier c3 = new Courier("Krystian", "Olejnik","570431267",15,asList(p1,p2),d1);
                Courier c4 = new Courier("Oskar", "Nawrocki","570434331",19,asList(p3),d2);

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
    }
}
