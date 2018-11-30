package pl.gda.pg.eti.kask.javaee.enterprise.users;


import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
public class RegistrationService {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserService userService;

    public void addUser(User user) throws ServletException {
        TypedQuery<User> query = em.createNamedQuery(User.Queries.FIND_BY_LOGIN, User.class);
        query.setParameter("login", user.getLogin());
        List<User> users = query.getResultList();
        if(users.size() == 0) {
            em.persist(user);
        }else{
            throw new ServletException();
        }
    }
}
