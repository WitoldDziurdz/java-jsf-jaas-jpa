package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.message.ErrorMessage;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@EqualsAndHashCode(of = "id",  callSuper = false)
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Department.Queries.FIND_ALL, query = "select d from Department d")
})
public class Department extends Audit implements Serializable {
    public static class Queries {
        public static final String FIND_ALL = "DEPARTMENT_FIND_ALL";
    }

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = ErrorMessage.NULL)
    @DecimalMin(value = "0", message = ErrorMessage.POSITIVE_NUMBER)
    private Integer numberOfWorkers;

    @NotBlank(message = ErrorMessage.TEXT)
    private String address;

    @NotNull(message = ErrorMessage.NULL)
    private boolean isStorage;

    @OneToMany(cascade = {ALL}, mappedBy = "department")
    private List<Courier> couriers;

    public Department(int numberOfWorkers, String address, boolean isStorage) {
        this.numberOfWorkers = numberOfWorkers;
        this.address = address;
        this.isStorage = isStorage;
    }
}

