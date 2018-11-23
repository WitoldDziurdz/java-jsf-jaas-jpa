package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 *
 * @author psysiu
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InPastValidator.class)
public @interface InPast {
    
    String message() default "{pl.gda.pg.eti.kask.javaee.enterprise.entities.validators.InPast.IN_PAST}";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String date() default "";
    
}
