package pt.isel.mpd.products.visitors;

import pt.isel.mpd.products.Pack;
import pt.isel.mpd.products.Promo;
import pt.isel.mpd.products.Speaker;
import pt.isel.mpd.products.TV;

public interface ElectronicsVisitor {
    void visit(Pack pack);
    void visit(TV tv);
    void visit(Speaker speaker);
    void visit(Promo promo);
}
