package pl.gda.pg.eti.kask.javaee.enterprise.web.converters;

import pl.gda.pg.eti.kask.javaee.enterprise.books.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Pack;

import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Pack.class, managed = true)
@Dependent
public class PackConverter extends AbstractEntityConverter<Pack> {
    public PackConverter() {
        super(Pack::getId, CourierService::findPack);
    }
}
