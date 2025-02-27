package pt.isel.mpd.products;

/**
 * Defines the contract for a product
 */
public interface Electronics {

    enum Category { AUDIO, VIDEO, INFORMATICS, COMMUNICATIONS, MULTI }

    String getName();               // get  product name
    double getPrice();              // get  product price
    Category getCategory();         // get  product category
    String getBrand();              // get  product brand
}
