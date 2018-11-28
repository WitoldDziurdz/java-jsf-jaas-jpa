package pl.gda.pg.eti.kask.javaee.enterprise.web.view.courier;

import lombok.Getter;
import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;
import pl.gda.pg.eti.kask.javaee.enterprise.events.CourierEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import java.util.Collection;

@Named
@ApplicationScoped
public class ListRecentCouriers implements CourierSource {
    @EJB
    CourierService courierService;

    @Getter
    Collection<Courier> couriers;

    @PostConstruct
    public void init() {
        couriers = courierService.findAllSortCouriers();
    }

    public void handleCourierEvent(@Observes CourierEvent courierEvent) {
        init();
    }
}
