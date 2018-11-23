package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

import java.io.Serializable;
import java.util.List;

public interface BookSource extends Serializable {
    List<Book> getBooks();
}
