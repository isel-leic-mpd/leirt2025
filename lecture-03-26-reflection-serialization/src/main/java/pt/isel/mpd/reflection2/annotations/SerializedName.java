package pt.isel.mpd.reflection2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerializedName {
    // using this special name "value"
    // we can associate the annotation to a field like this:
    //@SerializedName("any string")
    String value();
    
    // if it was not "value" but, for instance, "name", as:
    // String name()
    // we have to associate the annotation like this:
    //@SerializedName(name="any string")
    
}
