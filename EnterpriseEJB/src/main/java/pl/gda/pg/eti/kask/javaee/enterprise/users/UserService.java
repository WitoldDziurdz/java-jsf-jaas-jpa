/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.enterprise.users;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;

    @Resource
    SessionContext sessionCtx;

    @RolesAllowed(User.Roles.ADMIN)
    public List<User> findAllUsers() {
        return findAllUsers(User.Graphs.WITH_ROLES);
    }

    @RolesAllowed(User.Roles.ADMIN)
    public List<User> findAllUsers(String entityGraphName) {
        EntityGraph<?> entityGraph = em.getEntityGraph(entityGraphName);
        TypedQuery<User> query = em.createQuery("SELECT i FROM User i", User.class);
        query.setHint("javax.persistence.loadgraph", entityGraph);
        List<User> users = query.getResultList();
        return users;
    }

    @RolesAllowed(User.Roles.ADMIN)
    public User findUserById(String id) {
        EntityGraph<?> entityGraph = em.getEntityGraph(User.Graphs.WITH_ROLES);
        TypedQuery<User> query = em.createNamedQuery(User.Queries.FIND_BY_ID, User.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.loadgraph", entityGraph);
        return query.getSingleResult();
    }

    @RolesAllowed(User.Roles.ADMIN)
    public User findUser(String login) {
        return findUserByLogin(login);
    }

    @RolesAllowed(User.Roles.ADMIN)
    public void saveUser(User user) {
        em.merge(user);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public User findCurrentUser() {
        String login = sessionCtx.getCallerPrincipal().getName();
        return findUserByLogin(login);
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.MANAGER, User.Roles.WORKER})
    public void changePassword(String newPassword, String oldPassword) throws ServletException {
        User user = findCurrentUser();
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            em.merge(user);
        }else {
            throw new ServletException();
        }
    }


    private User findUserByLogin(String login) {
        EntityGraph<?> entityGraph = em.getEntityGraph(User.Graphs.WITH_ROLES);
        TypedQuery<User> query = em.createNamedQuery(User.Queries.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        query.setHint("javax.persistence.loadgraph", entityGraph);
        return query.getSingleResult();
    }
}
