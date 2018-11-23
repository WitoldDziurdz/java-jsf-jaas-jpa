package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("comics")
public class Comics extends Book {

    @Getter
    @Setter
    private int volume;

    public Comics(String title, Cover cover, Date publishDate, int volume, List<Author> authors, User owner) {
        super(title, cover, publishDate, authors, owner);
        this.volume = volume;
    }

}
