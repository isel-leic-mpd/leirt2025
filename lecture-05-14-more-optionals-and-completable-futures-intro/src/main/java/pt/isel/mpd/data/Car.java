package pt.isel.mpd.data;

public class Car {
    private Insurance insurance;
    private String brand;
    private String licenseNumber;


    public Car(String licenseNumber,  String brand, Insurance insurance) {
        this.licenseNumber = licenseNumber;
        this.brand = brand;
        this.insurance = insurance;
    }

    public Car(String licenseNumber,  String brand) {
       this(licenseNumber,  brand, null);
    }

    public Insurance getInsurance() { return insurance; }

    public String getLicense() { return licenseNumber; }

    public String getBrand() { return brand; }

}
