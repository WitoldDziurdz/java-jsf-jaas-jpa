package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.message.ErrorMessage;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
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
        @NamedQuery(name = Pack.Queries.FIND_ALL, query = "select p from Pack p"),
        @NamedQuery(name = Pack.Queries.FIND_PACKS, query = "select p from Pack p where p.price > :price")
})
public class Pack implements Serializable {
    public static class Queries {
        public static final String FIND_ALL = "PACK_FIND_ALL";
        public static final String FIND_PACKS = "PACK_FIND_BY_PRICE";
    }

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = ErrorMessage.TEXT)
    private String address;

    @NotNull(message = ErrorMessage.NULL)
    private TypeSize typeSize;

    @NotNull(message = ErrorMessage.POSITIVE_NUMBER)
    @DecimalMin(value = "0", message = ErrorMessage.POSITIVE_NUMBER)
    private Double price;

    @NotNull(message = ErrorMessage.NULL)
    private boolean express;

    @ManyToMany(mappedBy = "packs",cascade={MERGE, REFRESH, DETACH})
    private List<Courier> couriers = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JsonIgnore
    private User owner;

    public Pack(String address, TypeSize typeSize, double price, boolean express, User owner) {
        this.address = address;
        this.typeSize = typeSize;
        this.price = price;
        this.express = express;
        this.owner = owner;
    }

    @Override
    public String toString(){
        return  this.id + " " + this.address;
    }
}

