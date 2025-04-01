package pt.isel.mpd.reflection2;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.reflection2.expressions.Add;
import pt.isel.mpd.reflection2.expressions.Const;
import pt.isel.mpd.reflection2.expressions.Expr;
import pt.isel.mpd.reflection2.expressions.parser.ParserExpr;
import pt.isel.mpd.reflection2.products3.TV;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pt.isel.mpd.reflection2.ReflectionUtils.*;

public class ReflectionTests {
    @Test
    public void checkInterfacesImplementedByTv() {
        
        var tvImplements = TV.class.getInterfaces();
        for(var inter : tvImplements) {
            System.out.println(inter.getName());
        }
    }
    
    @Test
    public void checkArrayListImplementsIterable() {
        
        var arrayListClass = ArrayList.class;
        for(var inter : arrayListClass.getInterfaces()) {
            System.out.println(inter.getName());
        }
    }
    
    @Test
    public void checkListImplementsInterfaces() {
        
        var listClass = List.class;
        for(var inter : listClass.getInterfaces()) {
            System.out.println(inter.getName());
        }
        assertTrue(ReflectionUtils.implementsInterface(ArrayList.class, Iterable.class));
    }
    
    @Test
    public void instanceOfTest() {
        var parser = new ParserExpr();
        var add = parser.parse("2+3");
        
        assertTrue(isInstanceOf(add, Expr.class));
    }
    
    @Test
    public void checkIfAddImplementsExprTest() {
        assertTrue(implementsInterface(Add.class, Expr.class));
    }
    
    @Test
    public void checkIfListImplementsIsCollection() throws NoSuchFieldException {
        assertTrue(implementsInterface(List.class, Collection.class));
    }
    
    @Test
    public void checkIfAListInstanceImplementsIsCollection() throws NoSuchFieldException {
        var l = new ArrayList<String>();
        assertTrue(isInstanceOf(l, Collection.class));
    }
    
    @Test
    public void getExprInterfaceReflectionType() throws  ClassNotFoundException {
        var cls = Class.forName("pt.isel.mpd.reflex.expressions.Expr");
        System.out.println(cls.getName());
    }
    
   
    @Test
    public void saveExpressionToJsonTest() {
        var parser = new ParserExpr();
        Expr add = parser.parse("2+3");
        var json = saveObject(add);
        System.out.println(json.toString(2));
        
    }
    
    @Test
    public void saveExpressionsListToJsonTest() {
        var parser = new ParserExpr();
        var list = new ArrayList<Expr>();
        list.add(new Const(2));
        list.add(parser.parse("2+3"));
        list.add(parser.parse("2*(3+4)"));
        
        var json = saveObject(list);
        System.out.println(json.toString(2));
        
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
            Collection<Expr> list2 = loadCollection(Expr.class, json);
            System.out.println(list2);
        }
        catch(Exception e )  {
            System.out.println("exception " + e + "!");
        }
    }
    
    @Test
    public void saveExpressionsArrayToJson2Test() {
        var exprContainer = new Expr[] {
            new Const(2),
            new Const(3),
            new Const(4)
        };
        
        var json = saveObject(exprContainer);
        System.out.println(json.toString(2));
        
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void useNonGenericCollectionAndCastTest() {
        var list = new ArrayList();
        list.add(new Const(2));
        list.add(new Const(3));
        list.add("teste");
        List<Expr> exprs = (List<Expr>) list;
        System.out.println(exprs);
    }
    
   
    @Test
    public void createAStringArrayByReflectionTest() {
        
        String[] newArray = createArray(String.class, 10);
        
        System.out.println(newArray.getClass());
        System.out.println(newArray.length);
        
    }
}
