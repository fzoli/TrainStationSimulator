package vonatszimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public class Sinlezaro extends Sinveg {

    private Sin sin;
    private boolean sintNyit;

    public Sinlezaro(Graphics2D rajzterulet, Point pozicio) {
        super(rajzterulet, pozicio);
    }

    @Override
    public Sin getKovetkezoSin(boolean forditottMenetirany) {
        if ( (sintNyit && !forditottMenetirany)
             ||
             (!sintNyit && forditottMenetirany) )
                return sin;
        else
                return null;
    }

    @Override
    public void addSin(Sin sin) {
        this.sin = sin;
        sintNyit = (sin.getSinveg(0) == this);
    }

    @Override
    protected void setMeret() {
        setMeret(new Dimension(11, 21));
    }

    @Override
    protected void setSzin() {
        setSzin(Color.LIGHT_GRAY);
    }

    @Override
    protected void setAlakzat() {
        setTeglalapAlakzat();
    }

}