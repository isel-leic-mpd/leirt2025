package pt.isel.mpd.data_with_optionals;

import java.util.Optional;

public class Person {
    private Optional<Car> car;
    private final String name;
    private final String address;

    public Person(String name, String address, Optional<Car> car)  {
        this.name = name;
        this.address = address;
        this.car = car;
    }

    public Person(String name, String address )  {
        this(name, address, Optional.empty());
    }

    public  Optional<Car> getCar() {
        return car;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
