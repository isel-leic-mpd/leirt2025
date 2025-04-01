package pt.isel.mpd.reflection2.products3;

import org.json.JSONObject;
import pt.isel.mpd.reflection2.exceptions.ReflectionException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Defines the contract for a product
 */
public interface Electronics {
   
    interface ElectroCreator {
        Electronics create(JSONObject json);
    }
//    ElectroCreator tvCreator = json -> TV.fromJson(json);
//    ElectroCreator tvCreator2 = TV::fromJson;
    
    Map<String, ElectroCreator> creators  =  Map.of(
        "TV",       TV::fromJson,
        "Pack",     Pack::fromJson,
        "Promo",    Promo::fromJson,
        "Speaker",  Speaker::fromJson
    );
    
    
    enum Category { AUDIO, VIDEO, INFORMATICS, COMMUNICATIONS, MULTI }

    static Electronics createFrom0(JSONObject json) {
        var typeName = json.getString("type");
        return switch(typeName) {
            case "TV" -> TV.fromJson(json);
            case "Promo" -> Promo.fromJson(json);
            case "Pack" -> Pack.fromJson(json);
            case "Speaker" -> Speaker.fromJson(json);
            default -> null;
        };
    }

    static Electronics createFrom1(JSONObject json) {
        return creators.get(json.getString("type"))
                       .create(json);
    }
    
    static Electronics createFrom(JSONObject json) {
         try {
             var typeName = json.getString("__type");
             var cls = Class.forName(typeName);
             var m = cls.getMethod("fromJson", JSONObject.class);
             var e = (Electronics) m.invoke(null, json);
             return e;
         }
         catch(ClassNotFoundException | NoSuchMethodException |
               IllegalArgumentException | IllegalAccessException |
             InvocationTargetException e) {
             throw new ReflectionException("Reflection error", e);
         }
    }
    
    String getName();               // get  product name
    double getPrice();              // get  product price
    Category getCategory();         // get  product category
    String getBrand();              // get  product brand
    JSONObject saveToJson();        // serialize as a Json Object
   
}
