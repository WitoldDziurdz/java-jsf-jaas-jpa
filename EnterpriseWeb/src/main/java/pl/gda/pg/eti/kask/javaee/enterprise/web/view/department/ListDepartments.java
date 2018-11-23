package pl.gda.pg.eti.kask.javaee.enterprise.web.view.department;

import pl.gda.pg.eti.kask.javaee.enterprise.books.CourierService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@RequestScoped
public class ListDepartments implements Serializable {

    @Inject
    private CourierService courierService;

    private Collection<Department> departments;

    public Collection<Department> getDepartments() {
        return departments != null ? departments : (departments = courierService.findAllDepartments());
    }

    public String removeDepartments(Department department) {
        courierService.removeDepartment(department);
        return "list_departments?faces-redirect=true";
    }
}