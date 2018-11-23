package pl.gda.pg.eti.kask.javaee.enterprise.web.view.department;

import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class ViewDepartment implements Serializable {

    @Getter
    @Setter
    private Department department;
}
