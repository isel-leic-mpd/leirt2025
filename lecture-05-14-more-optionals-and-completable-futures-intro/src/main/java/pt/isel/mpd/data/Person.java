package pt.isel.mpd.data;

public class Person {
    private Car car;
    private final String name;
    private final String address;

    public Person(String name, String address, Car car)  {
        this.name = name;
        this.address = address;
        this.car = car;
    }

    public Person(String name, String address )  {
        this(name, address, null);
    }

    public Car getCar() {
        return car;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
