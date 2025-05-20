package pt.isel.mpd.data;

import java.time.LocalDate;

public class PersonUtils {

    public static String getPersonCarBrand(Person person) {
        Car personCar = person.getCar();
        if (personCar != null) {
           return personCar.getBrand();
        }
        return null;
    }
    
    public static LocalDate getPersonCarInsuranceDate(Person person) {

        Car personCar = person.getCar();
        if (personCar != null) {
            Insurance insurance = personCar.getInsurance();
            if (insurance != null) {
                return insurance.getExpirationDate();
            }
        }
        return null;
    }

    


}
