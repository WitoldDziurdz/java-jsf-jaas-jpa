package pl.gda.pg.eti.kask.javaee.enterprise.web.converters;

import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@RequestScoped
public class AuthorConverter implements Converter {

    @EJB
    BookService bookService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Author author = bookService.findAuthor(Integer.parseInt(value));

        if (author == null) {
            context.getExternalContext().setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
            context.responseComplete();
        }

        return author;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Author author = (Author) value;
        return Integer.toString(author.getId());
    }
}
