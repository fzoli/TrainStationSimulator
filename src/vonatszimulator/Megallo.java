package vonatszimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Megallo extends KeretesElem {

    private boolean piros = true;
    private boolean valtoMegallo;

    private Dimension meret;
    private int pozicio;
    private boolean forditottMenetirany;
    private Sin sin;

    public Megallo(Graphics2D rajzfelulet, Sin sin, int pozicio) {
        this(rajzfelulet, sin, pozicio, false, false);
    }

    public Megallo(Graphics2D rajzfelulet, Sin sin, int pozicio, boolean forditottMenetirany) {
        this(rajzfelulet, sin, pozicio, forditottMenetirany, false);
    }

    public Megallo(Graphics2D rajzfelulet, Sin sin, int pozicio, boolean forditottMenetirany, boolean valtoMegallo) {
        super(rajzfelulet);
        this.sin = sin;
        this.pozicio = pozicio;
        this.forditottMenetirany = forditottMenetirany;
        this.valtoMegallo = valtoMegallo;
        inicializalas();
    }

    @Override
    protected void inicializalas() {
        setMeret();
        sin.addMegallo(this, forditottMenetirany);
        super.inicializalas();
    }

    public void setPiros(boolean piros) {
        this.piros = piros;
    }

    public boolean valtoMegallo() {
        return valtoMegallo;
    }

    public boolean piros() {
        return piros;
    }

    public boolean isForditottMenetirany() {
        return forditottMenetirany;
    }

    protected int getPozicio() {
        return pozicio;
    }

    private void setMeret() {
        meret = new Dimension(10, 10);
    }

    @Override
    protected void setKeretvastagsag() {
        setKeretvastagsag(2);
    }

    @Override
    protected void setKeretszin() {
        setKeretszin(ALAPSZIN);
    }

    @Override
    protected void setAlakzat() {
        Point a, b, c;
        int magassagFele = (int)Math.round(meret.height/2);
        a = new Point(sin.getPont(pozicio));
        b = new Point(a.x, a.y - magassagFele);
        c = new Point(a.x, a.y + magassagFele);
        if (!forditottMenetirany) {
            b.setLocation(b.getX() - meret.width, b.getY());
            c.setLocation(c.getX() - meret.width, c.getY());
        }
        else {
            b.setLocation(b.getX() + meret.width, b.getY());
            c.setLocation(c.getX() + meret.width, c.getY());
        }
        int[] x = {a.x, b.x, c.x};
        int[] y = {a.y, b.y, c.y};
        setAlakzat(new Polygon(x, y, 3));
    }

    @Override
    protected void setSzin() {
        if (valtoMegallo) setSzin(new Color(200, 170, 50));
        else setSzin(new Color(173, 51, 48));
    }

}