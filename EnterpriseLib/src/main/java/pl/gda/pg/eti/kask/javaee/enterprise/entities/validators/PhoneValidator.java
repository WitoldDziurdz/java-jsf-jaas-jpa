package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PhoneValidator implements ConstraintValidator<PhoneAnnotate, String> {
    @Override
    public void initialize(PhoneAnnotate constraintAnnotation) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        if(value.length() != 9){
            return false;
        }
        try
        {
            Integer num = Integer.parseInt(value);
            if(num < 0 ){
                return false;
            }
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
