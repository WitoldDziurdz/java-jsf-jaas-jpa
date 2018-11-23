package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import pl.gda.pg.eti.kask.javaee.enterprise.events.BookEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class BookEventPropagator {

    @Inject
    @Push
    PushContext bookUpdates;

    public void handleBookEvent(@Observes BookEvent bookEvent) {
        bookUpdates.send("update");
    }
}
