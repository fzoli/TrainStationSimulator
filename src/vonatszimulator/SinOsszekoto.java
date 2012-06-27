package vonatszimulator;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public class SinOsszekoto extends Sinveg {

    private Sin[] sinek = new Sin[2];

    public SinOsszekoto(Graphics2D rajzterulet, Point pozicio) {
        super(rajzterulet, pozicio);
    }

    @Override
    public Sin getKovetkezoSin(boolean forditottMenetirany) {
        if (forditottMenetirany) return sinek[0];
        else return sinek[1];
    }

    @Override
    public void addSin(Sin sin) {
        if (sin.getSinveg(0) == this)
            sinek[1] = sin;
        if (sin.getSinveg(1) == this)
            sinek[0] = sin;
    }

    @Override
    protected void setMeret() {
        setMeret(new Dimension(2, 2));
    }

    @Override
    protected void setSzin() {
        setSzin(getKeretszin());
    }

    @Override
    protected void setAlakzat() {
        setTeglalapAlakzat();
    }

}