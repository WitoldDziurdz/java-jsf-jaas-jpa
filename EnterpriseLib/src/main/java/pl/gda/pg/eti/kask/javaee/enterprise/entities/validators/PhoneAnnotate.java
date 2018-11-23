package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.message.ErrorMessage;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface PhoneAnnotate {
    String message() default ErrorMessage.PHONE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String date() default "";
}