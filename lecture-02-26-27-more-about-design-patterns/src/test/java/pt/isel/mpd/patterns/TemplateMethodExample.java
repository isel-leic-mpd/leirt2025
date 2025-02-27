package pt.isel.mpd.patterns;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TemplateMethodExample {
    
    record Person(String name, int age) implements Comparable<Person> {
        public int compareTo(Person o) {
            return name.compareTo(o.name);
        }
    }
    
    
    @Test
    public void sorterTest() {
        var persons = new ArrayList<> (List.of(
            new Person("Ana", 33),
            new Person("Rui", 30),
            new Person("Joao", 25)
        ));
        // Sort by name
        
        Collections.sort(persons, Comparator.comparing(Person::age));
        //Collections.sort(persons, (p1, p2) -> p1.age - p2.age);
        
        
        persons.forEach(System.out::println);
    }
}
