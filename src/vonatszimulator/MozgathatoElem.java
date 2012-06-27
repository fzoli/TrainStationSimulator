package vonatszimulator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class MozgathatoElem extends KeretesElem {

    private Graphics2D rajzterulet = getRajzterulet();

    public MozgathatoElem(Graphics2D rajzterulet) {
        super(rajzterulet);
    }

    protected void eltunik() {
        setRajzteruletTorlesre();
        Shape torloAlakzat = createTorloAlakzat();
        rajzterulet.fill(torloAlakzat);
    }

    private void setRajzteruletTorlesre() {
        rajzterulet.setComposite(
                AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f)
                );
    }

    protected abstract Shape createTorloAlakzat();

    protected abstract void mozdul();
}