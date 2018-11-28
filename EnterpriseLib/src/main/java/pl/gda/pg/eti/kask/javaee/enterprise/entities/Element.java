package pl.gda.pg.eti.kask.javaee.enterprise.entities;

public interface Element {
    Integer getId();
    void setOwner(User owner);
    User getOwner();
}
