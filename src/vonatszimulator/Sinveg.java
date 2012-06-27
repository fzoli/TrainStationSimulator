package vonatszimulator;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public abstract class Sinveg extends KeretesElem {
    private Point pozicio;
    private Dimension meret;
    private Graphics2D rajzterulet = getRajzterulet();

    public Sinveg(Graphics2D rajzterulet, Point pozicio) {
        super(rajzterulet);
        this.pozicio = pozicio;
        inicializalas();
    }

    protected Dimension getMeret() {
        return meret;
    }

    protected void setMeret(Dimension meret) {
        if (meret == null) meret = new Dimension(2, 2);
        this.meret = meret;
    }

    protected Point getPozicio() {
        return pozicio; //utódok is ezt használják
    }

    protected Point createAlakzatRajzpont() {
        Point p = new Point();
        Dimension meretFele = new Dimension();
        meretFele.width = meret.width/2;
        meretFele.height = meret.height/2;
        p.x = pozicio.x - meretFele.width;
        p.y = pozicio.y - meretFele.height;
        return p;
    }

    @Override
    protected void inicializalas() {
        setMeret();
        super.inicializalas();
    }
    
    protected void setTeglalapAlakzat() {
        Point alakzatPozicio = createAlakzatRajzpont();
        setAlakzat(
            new Rectangle2D.Float(
                alakzatPozicio.x,
                alakzatPozicio.y,
                getMeret().width,
                getMeret().height));
    }

    protected void setEllipszisAlakzat() {
        Point alakzatPozicio = createAlakzatRajzpont();
        setAlakzat(
            new Ellipse2D.Float(
                alakzatPozicio.x,
                alakzatPozicio.y,
                getMeret().width,
                getMeret().height));
    }

    protected abstract void setMeret();

    public abstract Sin getKovetkezoSin(boolean forditottMenetirany);

    protected Sin getKovetkezoSin() {
        return getKovetkezoSin(false);
    }

    @Override
    protected void setKeretvastagsag() {
        setKeretvastagsag(2);
    }
    
    public abstract void addSin(Sin sin);

    @Override
    protected void setKeretszin() {
        setKeretszin(ALAPSZIN);
    }

}