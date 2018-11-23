package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@ToString(exclude = "books")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = Author.Queries.FIND_ALL, query = "SELECT a FROM Author a"),
        @NamedQuery(name = Author.Queries.COUNT_ALL, query = "SELECT COUNT(a) FROM Author a"),
        @NamedQuery(name = Author.Queries.COUNT_BOOKS_PER_AUTHOR, query = "SELECT a, COUNT(b) FROM Author a LEFT JOIN a.books b GROUP BY a"),
})
public class Author extends Audit implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Author.findAll";
        public static final String COUNT_ALL = "Author.countAll";
        public static final String COUNT_BOOKS_PER_AUTHOR = "Author.countBooksPerAuthor";
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Size(min = 2, max = 50)
    @NotBlank
    private String name;

    @Getter
    @Setter
    @Size(min = 2, max = 50)
    @NotBlank
    private String surname;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @JsonIgnore
    public String getFullName() {
        return format("%s %s", this.getName(), this.getSurname());
    }
}
