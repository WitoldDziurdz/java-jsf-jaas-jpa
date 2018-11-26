package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.DETACH;

@ToString(of = "title")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = Book.Queries.FIND_ALL, query = "SELECT b FROM Book b ORDER BY b.creationDate"),
        @NamedQuery(name = Book.Queries.FIND_MOST_RECENT, query = "SELECT b FROM Book b ORDER BY b.creationDate DESC"),
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("book")
public class Book extends Audit implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Book.findAll";
        public static final String FIND_MOST_RECENT = "Book.findMostRecent";
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Size(min = 3, max = 200)
    private String title;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Cover cover = Cover.SOFT;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    @Getter
    @Setter
    @Size(min = 1)
    @ManyToMany(cascade = {DETACH})
    private List<Author> authors = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JsonIgnore
    private User owner;

    public Book(String title, Cover cover, Date publishDate, List<Author> authors, User owner) {
        this.title = title;
        this.cover = cover;
        this.publishDate = publishDate;
        this.authors = authors;
        this.owner = owner;
    }
}
