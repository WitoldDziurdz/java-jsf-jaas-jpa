package pl.gda.pg.eti.kask.javaee.enterprise.books;

import pl.gda.pg.eti.kask.javaee.enterprise.books.auth.OwnerOrAdminAllowed;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;
import pl.gda.pg.eti.kask.javaee.enterprise.events.BookEvent;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.BookCreation;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.BookDeletion;
import pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers.BookModification;
import pl.gda.pg.eti.kask.javaee.enterprise.users.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Stateless
public class BookService implements Serializable {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserService userService;

    @Inject
    Event<BookEvent> bookEvent;


    @PermitAll
    public List<Book> findAllBooks() {
        return em.createNamedQuery(Book.Queries.FIND_ALL, Book.class).getResultList();
    }

    @PermitAll
    public List<Book> findRecentBooks(int limit) {
        TypedQuery<Book> query = em.createNamedQuery(Book.Queries.FIND_MOST_RECENT, Book.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
    public Book findBook(int id) {
        return em.find(Book.class, id);
    }

    @RolesAllowed(User.Roles.ADMIN)
    public void removeBook(Book book) {
        book = em.merge(book);
        em.remove(book);
        bookEvent.select(BookDeletion.Literal).fire(BookEvent.of(book));
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
    @OwnerOrAdminAllowed
    public Book saveBook(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            bookEvent.select(BookCreation.Literal).fire(BookEvent.of(book));
        } else {
            book = em.merge(book);
            bookEvent.select(BookModification.Literal).fire(BookEvent.of(book));
        }

        return book;
    }

    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
    public void mergeWithExisting(Book book) {
        Book originalBook = em.find(Book.class, book.getId());
        book.setOwner(originalBook.getOwner());
    }

    @PermitAll
    public List<Author> findAllAuthors() {
        return em.createNamedQuery(Author.Queries.FIND_ALL, Author.class).getResultList();
    }

    @PermitAll
    public Author findAuthor(int id) {
        return em.find(Author.class, id);
    }

    @PermitAll
    public Map<Author, Long> countBooksPerAuthor() {
        TypedQuery<Object[]> query = em.createNamedQuery(Author.Queries.COUNT_BOOKS_PER_AUTHOR, Object[].class);
        List<Object[]> results = query.getResultList();
        Map<Author, Long> booksCountPerAuthor = results.stream()
                .collect(toMap(result -> (Author) result[0], result -> (Long) result[1]));
        return booksCountPerAuthor;
    }

    @RolesAllowed(User.Roles.ADMIN)
    public Author saveAuthor(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            author = em.merge(author);
        }

        return author;
    }
}
