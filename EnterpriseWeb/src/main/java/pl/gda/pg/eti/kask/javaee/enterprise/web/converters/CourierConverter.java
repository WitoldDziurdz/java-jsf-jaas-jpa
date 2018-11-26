package pl.gda.pg.eti.kask.javaee.enterprise.web.converters;

import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;

import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Courier.class, managed = true)
@Dependent
public class CourierConverter extends AbstractEntityConverter<Courier>{
    public CourierConverter() {
        super(Courier::getId, CourierService::findCourier);
    }
}
