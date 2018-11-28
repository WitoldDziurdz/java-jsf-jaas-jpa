package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Audit implements Serializable {

    @Version
    private Integer version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JsonIgnore
    protected User owner;

    @PrePersist
    public void prePersist() {
        creationDate = modificationDate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        modificationDate = new Date();
    }
}
