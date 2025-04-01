package pt.isel.mpd.reflection2.products3;

import org.json.JSONObject;

public abstract class BaseElectronics implements Electronics {

    private final String name;
    private final String brand;
    private final double price;
    
    protected BaseElectronics(String name, String brand, double price)  {
        this.name = name; this.brand = brand; this.price = price;
    }
    
    // Product implementation
    
    @Override
    public String getName()             { return this.name;      }
    
    @Override
    public double getPrice()            { return this.price;     }
    
    @Override
    public String  getBrand()           { return brand;          }

    
    public String toString() {
        String s = String.format("%s %s: price %.2f euros",brand, name,price);
        return s;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (getClass() != other.getClass()) return false;
        var o = (Electronics) other;
        return getName().equalsIgnoreCase(o.getName()) &&
           getBrand().equalsIgnoreCase(o.getBrand());
           
    }

    @Override
    public JSONObject saveToJson() {
        JSONObject json = new JSONObject();
        json.put("type", getClass().getSimpleName());
        json.put("__type", getClass().getName());
        json.put("name", name);
        json.put("brand", brand);
        json.put("price", price);
        return json;
    }

}
