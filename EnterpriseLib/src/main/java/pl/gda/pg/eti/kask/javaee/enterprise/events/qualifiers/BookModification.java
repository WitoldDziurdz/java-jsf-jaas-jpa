package pl.gda.pg.eti.kask.javaee.enterprise.events.qualifiers;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface BookModification {
    Annotation Literal = new AnnotationLiteral<BookModification>() { };
}
