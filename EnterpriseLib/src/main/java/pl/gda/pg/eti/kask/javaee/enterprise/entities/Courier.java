package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.message.ErrorMessage;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.validators.PhoneAnnotate;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@EqualsAndHashCode(of = "id",  callSuper = false)
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Courier.Queries.FIND_ALL, query = "select c from Courier c"),
        @NamedQuery(name = Courier.Queries.FIND_ALL_SORT, query = "select c from Courier c order by c.modificationDate desc")
})
public class Courier extends Audit implements Element, Serializable {
    public static class Queries {
        public static final String FIND_ALL = "COURIER_FIND_ALL";
        public static final String FIND_ALL_SORT = "COURIER_FIND_ALL_SORT";
    }

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = ErrorMessage.TEXT)
    private String name;

    @NotBlank(message = ErrorMessage.TEXT)
    private String surname;

    @NotBlank(message = ErrorMessage.PHONE)
    @PhoneAnnotate
    private String phone;

    @NotNull(message = ErrorMessage.AGE)
    private Integer age;

    @ManyToOne(cascade = {MERGE, REFRESH})
    Department department;

    @ManyToMany(cascade={MERGE, REFRESH, DETACH})
    private List<Pack> packs = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JsonIgnore
    private User owner;

    public Courier(String name, String surname, String phone, int age, List<Pack> packs, Department department, User owner) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.age = age;
        this.packs = packs;
        this.department = department;
        this.owner = owner;
    }

    @Override
    public String toString(){
        return  this.name + " " + this.surname;
    }
}