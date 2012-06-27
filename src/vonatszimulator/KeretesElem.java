package vonatszimulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class KeretesElem extends LathatoElem {
    private Graphics2D rajzterulet = getRajzterulet();
    private Color keretszin;
    private BasicStroke stroke;

    public KeretesElem(Graphics2D rajzterulet) {
        super(rajzterulet);
    }

    @Override
    protected void inicializalas() {
        setKeretszin();
        setKeretvastagsag();
        super.inicializalas();
    }

    @Override
    protected void kirajzolodik() {
        super.kirajzolodik();
        keretKirajzolas();
    }

    protected void keretKirajzolas() {
        rajzterulet.setColor(keretszin);
        rajzterulet.fill(stroke.createStrokedShape(getAlakzat()));
    }

    protected float getKeretvastagsag() {
        return stroke.getLineWidth();
    }

    protected void setKeretvastagsag(float vastagsag) {
        stroke = new BasicStroke(vastagsag);
    }

    protected abstract void setKeretszin();

    protected abstract void setKeretvastagsag();

    protected Color getKeretszin() {
        return keretszin;
    }

    protected void setKeretszin(Color keretszin) {
        if (keretszin == null) keretszin=ALAPSZIN;
        this.keretszin = keretszin;
    }

}