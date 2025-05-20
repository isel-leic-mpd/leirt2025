package pt.isel.mpd.data_with_optionals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.Optional;
import static pt.isel.mpd.data_with_optionals.PersonUtils.*;



public class OptionalTests {

    @Test
    public void person_get_car_brand_on_person_without_car() {
        Person person =
            new Person("Armando", "Rua A");

        var carBrand =
            getPersonCarBrand(person).orElse("NoCar");
        
        assertEquals("NoCar", carBrand);
    }

    @Test
    public void person_get_car_brand_on_person_with_car() {
        Person person =
            new Person("Armando", "Rua A",
                Optional.of(new Car("XX-YY-ZZ", "Ford")));

        var carBrand =
            getPersonCarBrand(person).orElse("NoCar");

        assertEquals("Ford", carBrand);
    }

    @Test
    public void person_get_insurance_expiration_date() {
        LocalDate expirationDate = LocalDate.of(2022, 8, 15);
        LocalDate onEmptyDate = LocalDate.of(1,1,1);
        Person person =
            new Person("Armando", "Rua A",
                Optional.of(new Car("XX-YY-ZZ", "Ford",
                        Optional.of(new Insurance("Fidelidade", expirationDate)))));

        var seenDate  = getPersonCarInsuranceDate(person)
                                    .orElse(onEmptyDate);
        
        assertEquals(expirationDate, seenDate);
    }
}
