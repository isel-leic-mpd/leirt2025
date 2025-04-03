package pt.isel.mpd.reflection2;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.reflection2.annotations.SerializedName;
import pt.isel.mpd.reflection2.annotations.Transient;
import pt.isel.mpd.reflection2.expressions.Const;
import pt.isel.mpd.reflection2.expressions.Expr;
import pt.isel.mpd.reflection2.expressions.parser.ParserExpr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mpd.reflection2.SerializationUtils.*;

public class SerializationTests {
    
    @Test
    public void saveAndLoadExpressionToJsonTest() {
        var parser = new ParserExpr();
        Expr add = parser.parse("2+3");
        var json = saveObject(add);
        System.out.println(json.toString(2));
        Expr add2 = loadObject(Expr.class, json);
        System.out.println(add2);
    }
    
    @Test
    public void saveExpressionsListToJsonTest() {
        var parser = new ParserExpr();
        var list = new ArrayList<Expr>();
        list.add(new Const(2));
        list.add(parser.parse("2+3"));
        list.add(parser.parse("2*(3+4)"));
        
        var json = saveCollection(list, Expr.class);
        System.out.println(json.toString(2));
    }
    
    @Test
    public void saveAndLoadExpressionsArrayToJsonTest() {
        var exprContainer = new Expr[] {
            new Const(2),
            new Const(3),
            new Const(4)
        };
        
        var json = saveArray(exprContainer);
        System.out.println(json.toString(2));
        Expr[] exprContainer2 = loadObject(Expr[].class, json);
        assertEquals(3, exprContainer2.length);
        for (var e: exprContainer2) {
            System.out.println(e);
        }
    }
    
    @Test
    public void saveAndLoadExpressionsListToJson2Test()  {
        var list = new ArrayList<>(List.of(
            new Const(2),
            new Const(3),
            new Const(4)
        ));
        var json = saveCollection(list, Expr.class);
        System.out.println(json.toString(2));
        try {
            Collection<Expr> list2 = loadObject(Collection.class, json);
            System.out.println(list2);
        }
        catch(Exception e )  {
            System.out.println("exception " + e + "!");
        }
    }
    
 
    static class Person {
        @SerializedName("@Birthday")
        private String birthday;
        
        @Transient
        private int age;
        
        private Person() {
        }
        
        Person(String birthday) {
            this.birthday = birthday;
            age =10;
        }
        
        int getAge() {
            // calculate and save age
            return 0;
        }
        
        @Override
        public String toString() {
            return String.format("birthday: %s, age: %d", birthday, age);
        }
    }
    
    @Test
    public void saveAndLoadPersonTest() {
        Person p = new Person("1975-09-17");
        var json = saveObject(p);
        System.out.println(json.toString(2));
        try {
            Person p2 = loadObject(Person.class, json);
            System.out.println(p2);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
