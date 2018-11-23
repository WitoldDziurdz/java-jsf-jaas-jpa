package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Comics;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Cover;
import pl.gda.pg.eti.kask.javaee.enterprise.web.view.auth.AuthContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
public class EditBook implements Serializable {

    @EJB
    BookService bookService;

    @Inject
    AuthContext authContext;

    @Inject
    Conversation conversation;

    @Getter
    @Setter
    private Book book;

    @Setter
    private boolean comics;

    @Getter
    List<Author> availableAuthors;

    @PostConstruct
    public void init() {
        availableAuthors = bookService.findAllAuthors();
        conversation.begin();
    }

    public Cover[] getCoverTypes() {
        return Cover.values();
    }

    public void preRenderView() {
        if (book == null) {
            book = comics ? new Comics() : new Book();
            book.setOwner(authContext.getCurrentUser());
        }
    }

    public String saveBook() {
        conversation.end();
        bookService.saveBook(book);
        return "/books/list_books.xhtml?faces-redirect=true";
    }

    public boolean isComics() {
        return book instanceof Comics;
    }

    public String cancel() {
        conversation.end();
        return "/books/list_books.xhtml?faces-redirect=true";
    }

    public String next() {
        return "/books/edit_book_2.xhtml";
    }

    public String back() {
        return "/books/edit_book_1.xhtml";
    }
}
