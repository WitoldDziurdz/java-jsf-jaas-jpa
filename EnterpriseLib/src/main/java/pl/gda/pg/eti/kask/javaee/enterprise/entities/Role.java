package pl.gda.pg.eti.kask.javaee.enterprise.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Role.Queries.FIND_ROLE, query = "select r from Role r where r.operation =:operation"),
})
public class Role implements Serializable {
    public static class Queries {
        public static final String FIND_ROLE = "FIND_ROLE";
    }
    public static class Operations{
        public static final String FIND_PACK = "findPack";
        public static final String FIND_COURIER = "findCourier";
        public static final String FIND_DEPARTMENT = "findDepartment";
        public static final String REMOVE_PACK = "removePack";
        public static final String REMOVE_COURIER = "removeCourier";
        public static final String REMOVE_DEPARTMENT = "removeDepartment";
        public static final String SAVE_PACK = "savePack";
        public static final String SAVE_COURIER = "saveCourier";
        public static final String SAVE_DEPARTMENT = "saveDepartment";
    }

    public static class Permission{
        public static final String GRANTED = "GRANTED";
        public static final String IF_OWNER = "IF_OWNER";
        public static final String DENIED = "DENIED";
    }

    public Role(final String role, final String operation, final String permission) {
        this.role = role;
        this.operation = operation;
        this.permission = permission;
    }

    @Id
    @GeneratedValue
    private Integer id;

    String role;
    String operation;
    String permission;
}
