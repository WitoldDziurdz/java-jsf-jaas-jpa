package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import lombok.Getter;
import org.omnifaces.util.Ajax;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static org.omnifaces.util.Messages.addGlobalError;

@Named
@ViewScoped
public class ListAuthors implements Serializable {

    @EJB
    BookService bookService;

    @Getter
    private List<Author> authors;

    @Inject
    Validator validator;

    @PostConstruct
    public void init() {
        authors = bookService.findAllAuthors();
    }

    public void onCellEdit(Author author) {
        Set<ConstraintViolation<Author>> violations = validator.validate(author);

        if(violations.isEmpty()) {
            if (author.getId() == null) {
                Ajax.oncomplete("updateAuthors();");
            }
            
            bookService.saveAuthor(author);
            init();
        } else {
            violations.forEach(v -> addGlobalError(format("%s: %s", v.getPropertyPath().toString(), v.getMessage())));
        }
    }

    public void newAuthor() {
        if (authors.get(authors.size() - 1).getId() != null) {
            authors.add(new Author());
        }
    }
}
