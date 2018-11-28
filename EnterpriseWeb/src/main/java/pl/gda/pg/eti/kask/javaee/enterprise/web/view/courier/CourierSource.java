package pl.gda.pg.eti.kask.javaee.enterprise.web.view.courier;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;

import java.io.Serializable;
import java.util.Collection;

public interface CourierSource extends Serializable {
    Collection<Courier> getCouriers();
}
