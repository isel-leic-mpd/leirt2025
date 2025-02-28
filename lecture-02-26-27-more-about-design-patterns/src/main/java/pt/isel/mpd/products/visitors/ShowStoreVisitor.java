package pt.isel.mpd.products.visitors;

import pt.isel.mpd.products.Pack;
import pt.isel.mpd.products.Promo;
import pt.isel.mpd.products.Speaker;
import pt.isel.mpd.products.TV;

import java.io.PrintWriter;
import java.io.Writer;

public class ShowStoreVisitor implements ElectronicsVisitor {
    private final PrintWriter writer;
    private int indentLevel;
    
    private void indent() {
        for(int i = 0; i < indentLevel; ++i) writer.print(' ');
    }
    
    private void incLevel() {
        indentLevel+= 4;
    }
    private void decLevel() {
        indentLevel -= 4;
    }
    
    public ShowStoreVisitor(Writer writer) {
        this.writer = new PrintWriter(writer);
    }
    @Override
    public void visit(Pack pack) {
        indent();
        writer.printf("Pack %s, price: %.1f euros\n", pack.getName(), pack.getPrice());
        incLevel();
        for(var e : pack) {
            e.accept(this);
        }
        decLevel();
    }
    
    @Override
    public void visit(TV tv) {
        indent();
        writer.printf("TV %s, brand: %s, price: %.1f euros, resolution: %s, size: %.1f inches\n",
            tv.getName(), tv.getBrand(), tv.getPrice(), tv.getResolution(), tv.getScreenSize());
    }
    
    @Override
    public void visit(Speaker speaker) {
        indent();
        writer.printf("Speaker %s, brand: %s, price: %.1f euros, power: %.1f watts\n",
            speaker.getName(), speaker.getBrand(), speaker.getPrice(), speaker.getPower());
    }
    
    @Override
    public void visit(Promo promo) {
        indent();
        writer.printf("Promo %s, discount: %d, final price: %.1f euros\n",
            promo.getName(), promo.getDiscount(), promo.getPrice());
        incLevel();
        promo.getOriginal().accept(this);
        decLevel();
    }
}
