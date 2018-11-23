package pl.gda.pg.eti.kask.javaee.enterprise.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;


@AllArgsConstructor(staticName = "of")
public class BookEvent {
    @Getter
    @Setter
    Book book;
}
