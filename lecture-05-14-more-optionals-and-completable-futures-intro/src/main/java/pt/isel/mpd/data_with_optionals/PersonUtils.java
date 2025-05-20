package pt.isel.mpd.data_with_optionals;


import pt.isel.mpd.data_with_optionals.Person;

import java.time.LocalDate;
import java.util.Optional;

public class PersonUtils {

    public static Optional<String> getPersonCarBrand(Person person) {
         return person.getCar().map(c -> c.getBrand());
    }
    
    public static Optional<LocalDate> getPersonCarInsuranceDate(Person person) {
        return  person.getCar()
            .flatMap(c -> c.getInsurance()).map(ins -> ins.getExpirationDate());
    }
    
}
