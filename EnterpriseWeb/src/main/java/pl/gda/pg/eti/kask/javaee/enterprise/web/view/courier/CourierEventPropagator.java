package pl.gda.pg.eti.kask.javaee.enterprise.web.view.courier;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import pl.gda.pg.eti.kask.javaee.enterprise.events.CourierEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class CourierEventPropagator {

    @Inject
    @Push
    PushContext courierUpdates;

    public void handleCourierEvent(@Observes CourierEvent courierEvent) {
        courierUpdates.send("update");
    }
}
