package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import lombok.Getter;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListBooks implements Serializable {

    @EJB
    BookService bookService;

    @Inject
    AuthContext authContext;

    @Getter
    private List<Book> books;

    @PostConstruct
    public void init() {
        books = bookService.findAllBooks();
    }

    public void removeBook(Book book) {
        bookService.removeBook(book);
        init();
    }

    public boolean canEdit(Book book) {
        return authContext.isUserInRole(User.Roles.ADMIN) ||
                book.getOwner().equals(authContext.getCurrentUser());
    }

    public boolean canRemove() {
        return authContext.isUserInRole(User.Roles.ADMIN);
    }
}
