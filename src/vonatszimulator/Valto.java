package vonatszimulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class Valto extends Sinveg {
    private BasicStroke vonalEltuntetoStroke;
    private boolean atvaltva;
    private Line2D.Float nemAtvaltottVonal;
    private Line2D.Float atvaltottVonal;
    private Sin[] sinek = new Sin[3];
    //0: átirányítás nélkül erre kerülnek,
    //1: átirányítással erre kerülnek,
    //2: ellenkező irányból erre kerülnek, ha nincs átváltva különben NULL
    private Graphics2D rajzterulet;
    private boolean forditottMenetirany;

    public Valto(Graphics2D rajzterulet, Point pozicio) {
        this(rajzterulet, pozicio, false);
    }

    public Valto(Graphics2D rajzterulet, Point pozicio, boolean forditottMenetirany) {
        super(rajzterulet, pozicio);
        this.forditottMenetirany = forditottMenetirany;
    }

    @Override
    protected void kirajzolodik() {
        super.kirajzolodik();
        rajzterulet = getRajzterulet();
        setAtvaltoVonal();
    }

    @Override
    protected void inicializalas() {
        super.inicializalas();
        setAtvaltva(false);
    }

    @Override
    protected void setMeret() {
        setMeret(new Dimension(13, 13));
    }

    @Override
    protected void setSzin() {
        setSzin(Color.GREEN);
    }

    @Override
    protected void setAlakzat() {
        setEllipszisAlakzat();
    }

    public void atvalt() {
        setAtvaltva(!atvaltva);
    }

    public boolean getAtvaltva() {
        return atvaltva;
    }

    private void setAtvaltva(boolean allapot) {
        atvaltva = allapot;
        valtovonalKirajzol();
    }

    private void setAtvaltoVonal() {
        vonalEltuntetoStroke = new BasicStroke(2);
        nemAtvaltottVonal = new Line2D.Float();
        atvaltottVonal = new Line2D.Float();
        int szelessegFele = getMeret().width/2;
        int hosszusagFele = getMeret().height/2;
        Point pozicio = getPozicio();
        nemAtvaltottVonal.setLine(
                pozicio.x - szelessegFele + getKeretvastagsag(),
                pozicio.y,
                pozicio.x + szelessegFele - getKeretvastagsag(),
                pozicio.y);
        atvaltottVonal.setLine(
                pozicio.x,
                pozicio.y - hosszusagFele + getKeretvastagsag(),
                pozicio.x,
                pozicio.y + hosszusagFele - getKeretvastagsag());
    }

    private void valtovonalKirajzol() {
        valtovonalEltuntet();
        rajzterulet.setColor(getKeretszin());
        if (!atvaltva) rajzterulet.draw(nemAtvaltottVonal);
        else rajzterulet.draw(atvaltottVonal);
    }

    private void valtovonalEltuntet() {
        rajzterulet.setColor(getSzin());
        if (!atvaltva)
            rajzterulet.fill(vonalEltuntetoStroke.createStrokedShape(atvaltottVonal));
        else
            rajzterulet.fill(vonalEltuntetoStroke.createStrokedShape(nemAtvaltottVonal));
    }

    @Override
    public Sin getKovetkezoSin(boolean forditottMenetirany) {
        boolean egyezoIrany = (forditottMenetirany == this.forditottMenetirany);
        if (egyezoIrany) {
            if (!atvaltva)
                return sinek[0];
            else
                return sinek[1];
        }
        else
            if (!atvaltva)
                return sinek[2];
            else
                return null;
    }

    @Override
    public void addSin(Sin sin) {
        if (sin instanceof OsszekotoSin)
            sinek[1] = sin;
        else {
            int index = (forditottMenetirany) ? 0 : 1;
            if(sin.getSinveg(index) == this)
                sinek[2] = sin;
            else
                sinek[0] = sin;
        }
    }
    
}