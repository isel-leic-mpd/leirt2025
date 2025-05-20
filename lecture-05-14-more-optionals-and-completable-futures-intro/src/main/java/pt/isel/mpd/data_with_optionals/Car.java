package pt.isel.mpd.data_with_optionals;

import java.util.Optional;

public class Car {
    private Optional<Insurance> insurance;
    private String brand;
    private String licenseNumber;


    public Car(String licenseNumber,  String brand, Optional<Insurance> insurance) {
        this.licenseNumber = licenseNumber;
        this.brand = brand;
        this.insurance =insurance;
    }

    public Car(String licenseNumber,  String brand) {
       this(licenseNumber,  brand, Optional.empty());
    }

    public  Optional<Insurance> getInsurance() { return insurance; }

    public String getLicense() { return licenseNumber; }

    public String getBrand() { return brand; }

}
