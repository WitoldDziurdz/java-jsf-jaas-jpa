package pl.gda.pg.eti.kask.javaee.enterprise.books.auth;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Interceptor
@OwnerOrAdminAllowed
@Priority(1000)
public class OwnerOrAdminAllowedInterceptor implements Serializable {

    @Resource
    SessionContext sessionCtx;

    @PersistenceContext
    EntityManager em;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object parameter = context.getParameters()[0];

        if (parameter instanceof Book) {
            Book book = (Book) parameter;

            if (isNewBook(book) || isAdmin() || isOwner(book)) {
                return context.proceed();
            }
        }

        throw new EJBAccessException("Client not authorized for this invocation");
    }

    private boolean isOwner(Book book) {
        String login = sessionCtx.getCallerPrincipal().getName();
        Book originalBook = em.find(Book.class, book.getId());
        em.detach(originalBook);
        return originalBook.getOwner().getLogin().equals(login);
    }

    private boolean isAdmin() {
        return sessionCtx.isCallerInRole(User.Roles.ADMIN);
    }

    private boolean isNewBook(Book book) {
        return book.getId() == null;
    }
}
