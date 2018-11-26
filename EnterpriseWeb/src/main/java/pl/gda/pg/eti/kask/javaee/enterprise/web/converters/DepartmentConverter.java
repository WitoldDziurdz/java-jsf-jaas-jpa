package pl.gda.pg.eti.kask.javaee.enterprise.web.converters;

import pl.gda.pg.eti.kask.javaee.enterprise.couriers.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;

import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Department.class, managed = true)
@Dependent
public class DepartmentConverter extends  AbstractEntityConverter<Department> {
    public DepartmentConverter() {
        super(Department::getId, CourierService::findDepartment);
    }
}
