package pl.gda.pg.eti.kask.javaee.enterprise.entities.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessage {
    public static final String TEXT = "Text powinien składać sie z minimum jednej litery.";
    public static final String PHONE = "Numer telefonu powinien składać z 9 liczb.";
    public static final String AGE = "Wiek powinien być powyżej 18.";
    public static final String NULL = "Nie moze byc puste";
    public static final String POSITIVE_NUMBER = "Liczba powinna być powyżej zera lub zero.";
}
